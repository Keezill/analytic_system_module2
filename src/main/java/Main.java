import service.UserService;
import utils.LiquibaseUtil;

public class Main {
    public static void main(String[] args) {
        new LiquibaseUtil().liquibaseStart();
        UserService userService = new UserService();
        userService.run();
    }
}
