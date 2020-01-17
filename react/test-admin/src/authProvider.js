import { Auth } from "aws-amplify";
import {func} from "prop-types";


function getCookie(name) {
    var v = document.cookie.match('(^|;) ?' + name + '=([^;]*)(;|$)');
    return v ? v[2] : null;
}

function checkSession() {
    console.log(getCookie("session"))
    return getCookie("session") && Date.parse(getCookie("session")) < Date.now() ;
}

const authProvider = {
    login: async ({ username, password }) => {
        try {
            const user = await Auth.signIn(username, password);
            localStorage.setItem('token', user.username);
            const name = username;
            if (user.challengeName === 'NEW_PASSWORD_REQUIRED'){
                await Auth.completeNewPassword(user, password,{name})
            }
            const now = new Date();
            const time = now.getTime();
            const expireTime = time + 600000;
            now.setTime(expireTime);
            document.cookie = "session=" +  now.toISOString()
            document.location.href="/";
        } catch (err) {
            if (err.code === 'UserNotConfirmedException') {
                // The error happens if the user didn't finish the confirmation step when signing up
                // In this case you need to resend the code and confirm the user
                // About how to resend the code and confirm the user, please check the signUp part
                alert('UserNotConfirmedException')
            } else if (err.code === 'PasswordResetRequiredException') {
                // The error happens when the password is reset in the Cognito console
                // In this case you need to call forgotPassword to reset the password
                // Please check the Forgot Password part.
                alert('PasswordResetRequiredException')
            } else if (err.code === 'NotAuthorizedException') {
                // The error happens when the incorrect password is provided
                alert('NotAuthorizedException')
            } else if (err.code === 'UserNotFoundException') {
                // The error happens when the supplied username/email does not exist in the Cognito user pool
                alert('UserNotFoundException')
            } else {
                alert(err.message);
            }
        }
        // localStorage.setItem('token', username);
        // document.location.href="/";
    },
    logout: () => {
        Auth.signOut()
        return Promise.resolve();
    },
    checkAuth: async () => await Auth.currentAuthenticatedUser()
        ? Promise.resolve()
        : Promise.reject(),
    checkError: (error) => {
        const status = error.status;
        if (status === 401 || status === 403) {
            Auth.signOut()
            return Promise.reject();
        }
        return Promise.resolve();
    },
    getPermissions: async () => {
        if(checkSession()){
          Auth.signOut();
        }
        const logged =  await Auth.currentAuthenticatedUser();
        return logged.getSignInUserSession().getAccessToken().payload["cognito:groups"]
    },
    // getUserMail: () => JSON.parse(localStorage.getItem('CognitoIdentityServiceProvider.7glfkpchuh2db75ehftqimukeu.f7784e4c-f691-47cb-9250-a1b7bccf92eb.userData')).UserAttributes[3].Value,
    getUserInfo: async () => Auth.currentUserInfo(),
    getUserMail: async () => await Auth.currentUserInfo(),
    getHeader: async () =>  `Bearer ${(await Auth.currentSession()).getIdToken().getJwtToken()}`
};

export default authProvider;