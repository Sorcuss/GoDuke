const domain = "http://127.0.0.1:5500";

function setCookie(cname, cvalue, exminutes) {
    var d = new Date();
    d.setTime(d.getTime() + (exminutes * 60 * 1000));
    var expires = "expires=" + d.toUTCString();
    document.cookie = cname + "=" + cvalue + ";" + expires + ";path=/";
}
document.getElementById("login").addEventListener("click", () => {
    setCookie("id", "0d4f711e-6056-401b-bb0b-9c127d125cd6", 30);
    window.location.href = domain + "/html/pane-recruiter.html";
})