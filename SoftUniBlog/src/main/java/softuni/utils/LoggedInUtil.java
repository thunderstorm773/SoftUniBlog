package softuni.utils;

import softuni.enums.Role;
import softuni.models.LoginModel;
import softuni.models.viewModels.ShowArticle;
import softuni.models.viewModels.ShowUser;
import softuni.staticData.Constants;
import javax.servlet.http.HttpSession;

public class LoggedInUtil {

    public static boolean isUserLoggedIn(HttpSession httpSession) {
        return getLoginModel(httpSession) != null;
    }

    public static boolean isUserArticleAuthor(ShowArticle article, HttpSession session) {
        LoginModel loginModel = getLoginModel(session);
        Long userId = loginModel.getId();
        Long authorId = article.getAuthor().getId();
        return userId.equals(authorId);
    }

    public static boolean isUserIsAdmin(HttpSession session) {
        LoginModel loginModel = getLoginModel(session);
        Role userRole = loginModel.getRole();
        return userRole == Role.ADMIN;
    }

    public static LoginModel getLoginModel(HttpSession httpSession) {
        return (LoginModel) httpSession.getAttribute(Constants.LOGIN_MODEL_NAME);
    }
}
