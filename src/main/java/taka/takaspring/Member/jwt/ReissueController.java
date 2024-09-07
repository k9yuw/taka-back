package taka.takaspring.Member.jwt;

import io.jsonwebtoken.ExpiredJwtException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Date;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import taka.takaspring.common.Api;

@Controller
@ResponseBody
public class ReissueController {

    private final JwtUtil jwtUtil;
    private final RefreshRepository refreshRepository;

    public ReissueController(JwtUtil jwtUtil, RefreshRepository refreshRepository) {
        this.jwtUtil = jwtUtil;
        this.refreshRepository = refreshRepository;
    }

    @Operation(summary = "Refresh token 재발급", description = "refreshToken을 사용하여 새로운 accessToken을 재발급한다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "accessToken 재발급에 성공하였습니다.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Api.class),
                            examples = @ExampleObject(value = "{\"status\": \"success\", \"message\": \"accessToken 재발급에 성공하였습니다.\", \"data\": \"newAccessToken\", \"statusCode\": 200}"))),
            @ApiResponse(responseCode = "400", description = "refreshToken이 만료되었습니다. 로그아웃이 필요합니다.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Api.class),
                            examples = @ExampleObject(value = "{\"status\": \"fail\", \"message\": \"refreshToken이 만료되었습니다. 로그아웃이 필요합니다.\", \"data\": null, \"statusCode\": 400}"))),
            @ApiResponse(responseCode = "500", description = "accessToken 재발급에 실패하였습니다.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Api.class),
                            examples = @ExampleObject(value = "{\"status\": \"error\", \"message\": \"accessToken 재발급에 실패하였습니다.\", \"data\": null, \"statusCode\": 500}")))
    })
    @PostMapping("/reissue")
    public ResponseEntity<Api<?>> reissue(HttpServletRequest request, HttpServletResponse response) {

        String refresh = null;
        Cookie[] cookies = request.getCookies(); // 쿠키를 배열로 가져옴
        if (cookies != null) {
            for (Cookie cookie : cookies) { // 쿠키 배열을 순회해서 refresh라는 key값을 가진 배열에 refreshToken값 저장
                if (cookie.getName().equals("refresh")) {
                    refresh = cookie.getValue();
                }
            }
        }

        if (refresh == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Api.fail("refreshToken이 존재하지 않습니다."));
        }

        // expired 되었는지 확인하고 예외처리
        try {
            jwtUtil.isExpired(refresh);
        } catch (ExpiredJwtException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Api.fail("refreshToken이 만료되었습니다. 로그아웃이 필요합니다."));
        }

        // 토큰이 refreshToken인지 확인 (카테고리는 발급 시 페이로드에 명시)
        String category = jwtUtil.getCategory(refresh);

        if (!category.equals("refresh")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Api.fail("유효하지 않은 refreshToken 입니다."));
        }

        // refreshToken이 DB(refreshRepository)에 저장되어 있는지 확인
        Boolean isExist = refreshRepository.existsByRefresh(refresh);
        if (!isExist) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Api.fail("유효하지 않은 refreshToken 입니다."));
        }

        // refreshToken이 맞고, 만료되지 않았을 경우 email, role 뽑아낸 후 새로운 accessToken을 발급
        String email = jwtUtil.getEmail(refresh);
        String role = jwtUtil.getRole(refresh);

        String newAccess = jwtUtil.createJwt("access", email, role, 600000L); // 10분 유효한 accessToken
        String newRefresh = jwtUtil.createJwt("refresh", email, role, 86400000L); // 24시간 유효한 refreshToken

        // refresh rotate 시 refreshRepository 에 기존의 refreshToken 삭제 후 새 refreshToken 저장
        refreshRepository.deleteByRefresh(refresh);
        addRefreshEntity(email, newRefresh, 86400000L);

        response.setHeader("access", newAccess);
        response.addCookie(createCookie("refresh", newRefresh));

        return ResponseEntity.ok(Api.ok(newAccess, "accessToken 재발급에 성공하였습니다."));
    }

    private void addRefreshEntity(String email, String refresh, Long expiredMs) {
        Date date = new Date(System.currentTimeMillis() + expiredMs);

        RefreshEntity refreshEntity = new RefreshEntity();
        refreshEntity.setEmail(email);
        refreshEntity.setRefresh(refresh);
        refreshEntity.setExpiration(date.toString());

        refreshRepository.save(refreshEntity);
    }

    private Cookie createCookie(String key, String value) {
        Cookie cookie = new Cookie(key, value);
        cookie.setMaxAge(24 * 60 * 60); // 쿠키 유효 기간 24시간
        cookie.setHttpOnly(true); // HTTP 전용 설정
        return cookie;
    }
}
