/**
 * 
 * @param {type} seconds
 * @returns {undefined}
 */
function hold_server_session(seconds) {

    console.log(seconds);

    setInterval(function () {

        $.post("/RDW1/holdSessionServer", {}, function (result) {
            console.log(result);
        });

    }, seconds * 0.9 * 1000);
}
