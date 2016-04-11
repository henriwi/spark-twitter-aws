$(function () {
    var protocol = location.protocol === "https:" ? "wss:" : "ws:";
    var ws = new WebSocket(protocol + "//" + location.host + "/ws");

    ws.onmessage = function (msg) {
        var data = JSON.parse(msg.data);
        render(data)
    }

    console.log("ready!");
});

function render(data) {
    $("#content").html("");
    data.forEach(function(value) {
        $("#content").append("<div class='element'>" + "#" + value.hashTag + " (" + value.count + ")" + "</div>");
    })
}