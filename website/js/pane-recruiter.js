const getRrecruiterInfo = 'https://lvt1dwlfy9.execute-api.us-east-1.amazonaws.com/GoDuke/recruiters';
const domain = "http://127.0.0.1:5500";


document.addEventListener('DOMContentLoaded', function () {
    var elems = document.querySelectorAll('.fixed-action-btn');
    var instances = M.FloatingActionButton.init(elems, 'top');
});

document.getElementById("logout").addEventListener("click", () => {
    console.log(document.cookie);
    document.cookie = "id" + "=" + "" + ";" + "expires=Thu, 01 Jan 1970 00:00:00 GMT" + ";path=/";
    console.log(document.cookie);
    window.location.href = "http://127.0.0.1:5500/html/login-recruiter.htm";
})





function getCookie(cname) {
    var name = cname + "=";
    var decodedCookie = decodeURIComponent(document.cookie);
    var ca = decodedCookie.split(';');
    for (var i = 0; i < ca.length; i++) {
        var c = ca[i];
        while (c.charAt(0) == ' ') {
            c = c.substring(1);
        }
        if (c.indexOf(name) == 0) {
            return c.substring(name.length, c.length);
        }
    }
    return "";
};
var getName = async function (auth) {
    const url = 'https://lvt1dwlfy9.execute-api.us-east-1.amazonaws.com/Recruiter/recruiters';
    try {
        const response = await fetch(url, {
            method: "POST",
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                'id': auth
            })
        });
        if (response.ok) {
            return await response.json();
        }

    } catch (error) {
        console.log(error);
    }
}

var setName = function (firstname, lastname) {
    var username = document.getElementById("username");
    username.innerHTML = firstname + " " + lastname;
}

async function init() {
    var auth = getCookie("id");
    console.log(auth);
    if (auth === null || auth === "") {
        window.location.href = "index.html";
    } else {
        var obj = await getName(auth);
        setName(obj["firstname"], obj["lastname"]);

    }
}
init();