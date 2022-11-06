package adapters.driver.console;

import io.github.cdimascio.dotenv.Dotenv;

public class GitConfigCheck {
    static void gitConfigCheck() {
        checkGitConfig("GITURL");
        checkGitConfig("ACCESSTOKEN");
    }

    static void checkGitConfig(String key) {
        Dotenv dotenv = null;
        dotenv = Dotenv.configure().load();

        if (dotenv.get(key) == null) {
            System.out.println(key + " not set in .env file");
        }
    }
}