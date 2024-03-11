app = (() => {

    function loadGetMsg() {
        let nameVar = document.getElementById("name").value;
        const xhttp = new XMLHttpRequest();
        xhttp.onload = function() {
            document.getElementById("getrespmsg").innerHTML =
            this.responseText;
        }
        xhttp.open("GET", "/loginservice?send="+nameVar);
        xhttp.send();
    }

    return {
        loadGetMsg
    };
})();