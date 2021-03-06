$(function () {
    // 清除输入框内容
    $('.modal').on('hidden.bs.modal', function () {
        $(this).find('form').get(0).reset();
    });
    /*作业布置截至时间选框*/
    $('#homeworkStopTime').datetimepicker({
        format: 'yyyy-mm-dd',
        minView: 2,
        autoclose: true,
        language: 'zh-CN'
    });
    //学生登录模块
    $('#student_login .confirm').click(function () {
        $.ajax({
            type: "POST",
            url: "",
            data: {
                student_num: $('#student_num').val(),
                student_pass: $('#student_pass').val()
            },
            success: function (data, statusText) {
                alert(data);
            }
        });
    });
    //老师登录模块
    $('#teacher_login_form .confirm').click(function () {
        $.ajax({
            type: "POST",
            url: "../login?operate=teacherLogin",
            data: {
                username: $('#teacher_num').val(),
                password: $('#teacher_pass').val()
            },
            success: function (data, statusText) {
                var jsondata = $.parseJSON(data);
                if (jsondata.status == 1) {
                    $.cookie('currentUser', $('#teacher_num').val(), {expires: 7})
                    window.location.href = "course-list.html";
                } else {
                    alert('登录失败，请重试');
                }
            },
        });
    });
    // 监测登录状态模块
    /*如果登录了进行后续操作，如果没有登录，跳转到登陆界面进行登录*/
    $.cookie('currentUser', '2013211729', {expires: 7})// TODO 测试数据，记得删除
    if ($.cookie('currentUser')) {
        $('#current_center').html($.cookie('currentUser') + '<span class="caret"></span>');
        addPlan();//TODO 考虑是否在这里执行
        addStudent();
        addHomework();
    } else if ($('#current_center').length > 0) {
        $('#current_center').html('登录');
    }
    //注销登录
    /*点击注销按钮，清除cookies*/
    $('#logout').click(function () {
        $.removeCookie('currentUser');
        $('#current_center').parent().html('<a href="index.html">登录</a>')
    });
    //添加课程进度模块
    /*字段合法性监测，通过点击确定允许提交*/
    function addPlan() {
        $('#addPlan .confirm').click(function () {
            $('#addPlan').find('form').submit();
        });
        $('#addPlan').find('form').validate({
            submitHandler: function (form) {
                $(form).ajaxSubmit({
                    type: 'POST',
                    url: '../schedule?operate=addSchedule',
                    success: function (data, statusText) {
                        if (data == 1) {
                            $(form).resetForm();
                            alert('添加成功');
                            $('#addPlan').modal('hide');
                            getCourseProcess();
                        } else {
                            alert(statusText);
                        }
                    }
                });
            },
            rules: {
                plan_time: {
                    required: true,
                },
                plan_text: {
                    required: true,
                }
            },
            messages: {
                plan_time: {
                    required: '亲，你没有填写时间哟！',
                },
                plan_text: {
                    required: '亲，你没有填写详细进度描述哟！',
                }
            },
            highlight: function (element, errorClass) {
                $(element).css('border', '1px solid red');
            },
            unhighlight: function (element, errorClass) {
                $(element).css('border', '1px solid #ccc');
            }
        });
    }

    //添加学生模块
    /*字段合法性监测，通过点击确定允许提交*/
    function addStudent() {
        $('#addStudent .confirm').click(function () {
            $('#addStudent').find('form').submit();
        });
        $('#addStudent').find('form').validate({
            submitHandler: function (form) {
                $(form).ajaxSubmit({
                    type: 'POST',
                    url: '../student?operate=add',
                    clearForm: true,
                    success: function (data, statusText) {
                        if(data==1) {
                            alert('添加学生成功');
                            $('#addStudent').modal('hide');
                            getStudentList();
                        }else if(data==2){
                            alert('学生已存在，请刷新浏览器进行查看！');
                        }else{
                            alert('发生错误，请重试');
                        }
                    }
                });
            },
            rules: {
                stuNum: {
                    required: true,
                    digits: true,
                },
                stuName:{
                    required:true,
                },
                classNum:{
                    required:true,
                    digits: true,
                },
                department:{
                    required:true,
                }
            },
            messages: {
                stuNum: {
                    required: '请填写学生的学号！',
                    digits:'学号应为纯数字组成！',
                },
                stuName:{
                    required:'请填写学生姓名',
                },
                classNum:{
                    required:'请填写学生所属班级',
                },
                department:{
                    required:'请填写学生所属学院',
                },
            },
            highlight: function (element, errorClass) {
                $(element).css('border', '1px solid red');
            },
            unhighlight: function (element, errorClass) {
                $(element).css('border', '1px solid #ccc');
            }
        });
    }
    function addHomework(){
        $('#addHomework .confirm').click(function () {
           $('#addHomework').find('form').submit();
        })
        $('#addHomework').find('form').validate({
            submitHandler: function (form) {
                $(form).ajaxSubmit({
                    type:'POST',
                    url:'../homework?operate=addHomework',
                    success: function (data,statusText) {
                        if(data==1){
                            alert('添加成功！');
                            $('#addHomework').modal('hide');
                            getHomeworkList();
                        }else if(data==0){
                            alert('发生错误，请重试！')
                        }
                    }
                });
            },
            rules:{
                homeworkStopTime:{
                    required:true,
                },
                homeworkTitle:{
                    required:true,
                }
            },
            messages:{
                homeworkStopTime:{
                    required:'请选择作业截至日期！',
                },
                homeworkTitle:{
                    required:'请填写作业题目',
                }
            },
            highlight: function (element, errorClass) {
                $(element).css('border', '1px solid red');
            },
            unhighlight: function (element, errorClass) {
                $(element).css('border', '1px solid #ccc');
            }
        });
    }
});