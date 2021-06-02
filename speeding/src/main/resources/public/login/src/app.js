function toastus(message, type) {
    $('body').toast({
    class: type ? type : 'success',
    displayTime: 4000,
    message: message
    });
}

function isEmpty(el) {
    return el == null || !el.toString().trim().length;
}

var main = new Vue({
    el: '#main',
    data: {
        login: null,
        password: null,
        repeatPassword: null,
        loginMode: true,
        isCreated: false
    },
    computed: {
        isRegOk: function() {
            return !isEmpty(this.login) && !isEmpty(this.password) && this.password == this.repeatPassword;
        },
        isLoginOk: function() {
            return !isEmpty(this.login) && !isEmpty(this.password);
        }
    },
    methods: {
        clearCredentials: function() {
            this.login = null;
            this.password = null;
            this.repeatPassword = null;
            this.loginMode = true;
        },
        logIn: function() {
            let obj = {login: this.login, password: this.password};
            let url = !this.loginMode ? "registration/": "login/";
            $.post(url, obj).then(function(data) {
                if (data.success) {
                    window.location.href = "../";
                } else {
                    toastus(data.message, data.type);
                }
            }).catch(function(data) {
                toastus('Auth Error', 'red');
            })
        }
    },
    mounted: function() {
        this.isCreated = true;
    }
});