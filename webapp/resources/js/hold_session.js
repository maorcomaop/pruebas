/**
 * 
 * @param {type} minutes
 * @returns {undefined}
 */
function hold_session(minutes) {
    var time = new Date().getTime();
    $(document.body).bind("mousemove keypress wheel click", function () {
        time = new Date().getTime();
    });

    setInterval(function () {
        if (new Date().getTime() - time >= minutes*60000) {
            window.location.reload(true);
        }
    }, 1000);
}
