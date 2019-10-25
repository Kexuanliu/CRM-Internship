/**
 * Created by Administrator on 2018/7/12.
 */
/**
 * Created by Administrator on 2018/7/12.
 */

jQuery(document).ready(function () {
    document.onkeydown = function () {
        var key = window.event.keyCode;
        var title = $('title').context.title;
        if(key == 13){
            if(title =='注册登录'){
               insertVue.login();
            }else if(title =='找回密码'){
                insertVue.findPwd();
            }else if(title =='手机号注册'){
                insertVue.register();
            }
        }
    }
    var insertVue = new Vue({
        el: '#insertVue',
        data: {
            realName: '',
            // 登录账号（手机号）
            tel: '18668108970',
            //账号密码
            pwd: 'q123456',
            //验证码
            captcha: '',
            showPage: 'telRegister',
            gender: 'MALE',
            mail: '',
            time: 60,
            flag: true,
            birth:'',
            title:'',

        },
        methods: {
            'login': function () {
                var thisVue = this;
                jQuery.ajax({
                    type: 'post',
                    url: '/login',
                    data: {
                        tel: thisVue.tel,
                        pwd: thisVue.pwd
                    },
                    dataType: 'json',
                    cache: false
                }).done(function (result) {
                    if (result.successFlg) {
                        window.location.href = "/myAccountInfor";
                    } else{
                        if(result.loginError) {
                            alert("用户名或密码不正确");
                        }else{
                            alert("用户名或密码不正确！");
                        }
                    }
                });
            },
            //忘记密码，找回密码
            'findPwd': function () {
                if (this.tel.length != 11) {
                    alert("请填写正确的11位手机号码！");
                } else if (this.captcha.length != 4) {
                    alert("请填写4位有效验证码");
                } else if (this.pwd.length < 6) {
                    alert("密码至少6位！");
                } else {
                    var thisVue = this;
                    jQuery.ajax({
                        type: 'post',
                        url: '/findPwd',
                        data: {
                            tel: thisVue.tel,
                            pwd: thisVue.pwd,
                            captcha: thisVue.captcha,
                        },
                        dataType: 'json',
                        cache: false
                    }).done(function (result) {
                        if (result.successFlg) {
                            window.location.href = "/";
                        } else {
                            alert(result.errMsg);
                        }
                    });
                }
            },
            'register': function () {
                if (this.tel.length != 11) {
                    alert("请填写正确的11位手机号码！");
                } else if (this.captcha.length != 4) {
                    alert("请填写4位有效验证码");
                } else if (this.realName == '') {
                    alert("请填写姓名信息");
                } else if (this.pwd.length < 6) {
                    alert("密码至少6位！");
                } else {
                    var thisVue = this;
                    jQuery.ajax({
                        type: 'post',
                        url: '/telRegister',
                        data: {
                            realName: thisVue.realName,
                            tel: thisVue.tel,
                            pwd: thisVue.pwd,
                            captcha: thisVue.captcha,
                        },
                        dataType: 'json',
                        cache: false
                    }).done(function (result) {
                        if (result.successFlg) {
                            thisVue.showPage = 'supplementaryInformation';
                        } else {
                            alert(result.errMsg);
                        }
                    });
                }
            },
            'getCaptchaRegister': function () {
                if (this.tel.length != 11) {
                    alert("请填写正确的11位手机号码！");
                } else {
                    if (this.flag) {
                        this.flag = false;
                        var thisVue = this;
                        jQuery.ajax({
                            type: 'post',
                            url: '/telRegister/captcha',
                            data: {
                                tel: thisVue.tel,
                            },
                            dataType: 'json',
                            cache: false
                        }).done(function (result) {
                            if (result.successFlg) {
                                $("#inputTel").attr("readonly","readonly");
                                $("#send-captcha").css('color', 'gray');
                                thisVue.reserveCode();
                            } else {
                                thisVue.flag = true;
                                alert(result.errMsg);
                            }
                        });
                    }
                }
            },
            'getCaptchaPwd': function () {
                if (this.tel.length != 11) {
                    alert("请填写正确的11位手机号码！");
                } else {
                    if (this.flag) {
                        this.flag = false;
                        var thisVue = this;
                        jQuery.ajax({
                            type: 'post',
                            url: '/findPwd/captcha',
                            data: {
                                tel: thisVue.tel,
                            },
                            dataType: 'json',
                            cache: false
                        }).done(function (result) {
                            if (result.successFlg) {
                                $("#inputTel").attr("readonly","readonly");
                                $("#send-captcha").css('color', 'gray');
                                thisVue.reserveCode();
                            } else {
                                thisVue.flag = true;
                                alert(result.errMsg);
                            }
                        });
                    }
                }
            },
            'confirm': function () {
                if(this.realName ==''){
                    alert("请填写姓名");
                }else if(this.tel.length!= 11){
                    alert("请填写11位有效手机号");
                }else if(this.birth == ''){
                    alert("请选择出生年月");
                }else if (this.gender == '') {
                    alert("请选择性别");
                }else if(this.mail == ''){
                    alert("请填写电子邮箱");
                }else {
                    var thisVue = this;
                    jQuery.ajax({
                        type: 'post',
                        url: '/supplementaryInformation/add',
                        data: {
                            realName: thisVue.realName,
                            tel: thisVue.tel,
                            pwd: thisVue.pwd,
                            birth:thisVue.birth,
                            gender: thisVue.gender,
                            mail: thisVue.mail,
                        },
                        dataType: 'json',
                        cache: false
                    }).done(function (result) {
                        if (result.successFlg) {
                            window.location.href = "/registerSuccess";
                        } else {
                            alert("信息填写错误，注册失败");
                        }
                    });
                }
            },
            'reserveCode': function () {
                var thisVue = this;
                var timer = setInterval(function () {
                    thisVue.time--;
                    $("#send-captcha").html("获取验证码" + thisVue.time + "s");
                    if (thisVue.time == 0) {
                        thisVue.time = 60;
                        $("#send-captcha").css('color', '#3CC51F');
                        $("#send-captcha").html("获取验证码");
                        clearInterval(timer);
                        thisVue.flag = true;
                        $("#inputTel").removeAttr("readonly");
                    }
                }, 1000)
            },
        }
    });
})
