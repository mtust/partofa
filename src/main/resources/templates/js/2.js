    toastr.options = {
        "closeButton": false,
        "debug": false,
        "newestOnTop": false,
        "progressBar": false,
        "positionClass": "toast-top-right",
        "preventDuplicates": false,
        "onclick": null,
        "showDuration": "300",
        "hideDuration": "1000",
        "timeOut": "5000",
        "extendedTimeOut": "1000",
        "showEasing": "swing",
        "hideEasing": "linear",
        "showMethod": "fadeIn",
        "hideMethod": "fadeOut"
    }
    $(function () {
        $('#login-form-link').click(function (e) {
            $("#login-form").delay(100).fadeIn(100);
            $("#register-form").fadeOut(100);
            $('#register-form-link').removeClass('active');
            $(this).addClass('active');
            e.preventDefault();
        });
        $('#register-form-link').click(function (e) {
            $("#register-form").delay(100).fadeIn(100);
            $("#login-form").fadeOut(100);
            $('#login-form-link').removeClass('active');
            $(this).addClass('active');
            e.preventDefault();
        });
    });



//    $("#register-submit").click(function () {
//        $.ajax({
//            type : 'post',
//            url: "/public/user/signup",
//            data : {
//                firstName : $("#firstName").val(),
//                lastName : $("#lastName").val(),
//                email : $("#email").val(),
//                password : $("#password").val(),
//                passwordConfirm : $("#passwordConfirm").val()
//            }
//            success : function (data) {
//                toastr[success]("Ви зареєстрованні")
//            },
//            error : function () {
//                toastr[error]("Помилка")
//            }
//        })
//    })


    $('#register-form').on('submit', function(e){
        e.preventDefault();
        $.ajax({
            type: 'post',
            url: '/public/user/signup',
            data: {
                $(this).serialize();
            },
            success: function (data) {
                toastr[success]("Ви зареєстрованні");
            },
            error: function (data) {
                toastr[error]("Помилка");
            }
        });
    });


    $("#login-submit").click(function(e){
        var dataPost = $("#login-form")
        $.ajax({
            type: 'post',
            url: '/login',
            data: {
                email: $("#username").val(),
                password: $("#passwordLogin").val()
            },
            success: function (data) {
                window.location.href = "/";
            },
            error: function(data) {
                if(data.responseJSON.message == "Authentication Failed: User is disabled"){
                    toastr["error"]("Цей користувач заблокований")
                } else {
                    toastr["error"]("Неправильно веддений логін або пароль")
                }
            }
        });
    });

 //   $('#register-form').on('submit', function(e) {
//        (toastr["success"]("Ви зареєструвались. Очікуйте підтвердження від адміністратора")).delay( 8000 )
//    });

    $('#restorePassword').click(function (e) {
        (toastr["success"]("Вам на пошту надсилається новий пароль!"));
        $.ajax({
            type: 'post',
            url: '/public/password/restore',
            data : {
                "email": $("#username").val()
            },
            success: function () {
                (toastr["success"]("Вам на пошту надіслано новий пароль!")).delay(8000);
            },
            error: function () {
                toastr["error"]("Помилка відправлення паролю. Перевірте правильність введеної пошти!");
            }
        })
    })
