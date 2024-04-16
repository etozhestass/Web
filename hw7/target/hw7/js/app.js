window.notify = function (message) {
    $.notify(message, {
        position: "right bottom",
        className: "success"
    });
}

window.ajax = function (options, $error) {
    const default_options = {
        type: "POST",
        url: "",
        dataType: "json",
        data: {},
        success: function (response) {},
        redirect: true,
    };

    options = {...default_options, ...options};
    $.ajax({
        type: options.type,
        url: options.url,
        dataType: options.dataType,
        data: options.data,
        success: function (response) {
            if (response["error"]) {
                $error.text(response["error"]);
            } else {
                options.success(response);
                if (options.redirect) {
                    location.href = response["redirect"];
                }
            }
        },
    });
}

