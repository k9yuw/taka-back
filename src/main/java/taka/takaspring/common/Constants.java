package taka.takaspring.common;

public class Constants {

    public enum ExceptionClass {

        AUTH("Auth"), RENT("Rent");

        private String exceptionClass;

        ExceptionClass(String exceptionClass) {
            this.exceptionClass = exceptionClass;
        }

        public String getExceptionClass() {
            return exceptionClass;
        }

        @Override
        public String toString() {
            return getExceptionClass() + "예외 발생.";
        }
    }

}
