$.postSpring = function(url, obj) {
    return new Promise(function(resolve, reject) {
        $.ajax(url, {
            data: JSON.stringify(obj),
            contentType: 'application/json',
            type: 'POST',
            success: function(data) {
                resolve(data);
            },
            error: function(data) {
                reject(data);
            }
        });
    });
}