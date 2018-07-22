!function(i,e){$.prototype.invalid=function(i){var e=$(this).find("[name='"+i+"']");e.addClass("error"),e.change(function(){e.removeClass("error"),e.off("change")})},$.prototype.invalidFields=function(i){for(var e=0;e<i.length;e+=1)this.invalid(i[e].path)},$.prototype.invalidText=function(i){if(!i)return void $(".form-validation").remove();var e=$("<div class='form-validation form-validation--error'><i class='fa fa-exclamation-circle'></i><span class='form-validation__text'>"+i+"</span></div>");$(this).prepend(e)};var t={submitBtn:$("#submit"),form:$("#user-register-form"),waitingPincode:!1,waitingSubmit:!1,pinCodeButton:$(".user-login__pincode"),init:function(){this.initEvent();var i=$("#countDown").text();i>0&&this.countDownPincode(i)},initEvent:function(){this.submitBtn.click(function(){this.form.invalidText();for(var i=this.form.serializeArray(),e={},n=0;n<i.length;n+=1){var a=i[n];e[a.name]=a.value}if(!t.validate(e))return!1;void 0===e.username&&(e.username=e.email),t.submit(e)}.bind(this)),this.pinCodeButton.click(function(){this.sendPinCode()}.bind(this))},sendPinCode:function(){if(!this.waitingPincode){this.form.invalidText();for(var i=this.form.serializeArray(),e={},t=0;t<i.length;t+=1){var n=i[t];e[n.name]=n.value}if(this.validateUserName(e.username)){var a={email:e.username};$.ajax({type:"POST",url:"/web/api/pincode",contentType:"application/json",dataType:"json",data:JSON.stringify(a),success:function(i){this.countDownPincode()}.bind(this),error:function(i){alert("upload failed")}})}}},countDownPincode:function(i){this.waitingPincode=!0;var e=i||60;this.pinCodeButton.addClass("waiting");var t=function(){this.pinCodeButton.attr("countdown",e),e-=1,e>0?setTimeout(t.bind(this),1e3):(this.pinCodeButton.removeClass("waiting"),this.waitingPincode=!1)}.bind(this);t()},validate:function(i){return this.validateUserName(i.username)?!!this.validatePassword(i.password)&&(!!i.pinCode||(this.form.invalid("pinCode"),!1)):(this.form.invalid("username"),!1)},validateUserName:function(i){var e=$("#userNameStrategy").val(),t=$("[name=username]");if("EMAIL"===e){if(!this.validateUsernameRegex(i,"^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@(([[0-9]{1,3}.[0-9]{1,3}.[0-9]{1,3}.[0-9]{1,3}])|(([a-zA-Z-0-9]+.)+[a-zA-Z]{2,}))$"))return this.form.invalidText(t.data("ruleNeedemail")),!1}else if("PHONE"===e){if(!this.validateUsernameRegex(i,"^1[3578][0-9]{9}$"))return this.form.invalidText(t.data("ruleNeedphone")),!1}else if("EMAIL_PHONE"===e){if(!this.validateUsernameRegex(i,"^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@(([[0-9]{1,3}.[0-9]{1,3}.[0-9]{1,3}.[0-9]{1,3}])|(([a-zA-Z-0-9]+.)+[a-zA-Z]{2,}))$")&&!this.validateUsernameRegex(i,"^1[3578][0-9]{9}$"))return this.form.invalidText(t.data("ruleNeedemailorphone")),!1}else if(!this.validateUsernameRegex(i,"^[a-zA-Z](1)[a-zA-Z0-9]+$"))return this.form.invalidText(t.data("ruleNeedusername")),!1;return!0},validateUsernameRegex:function(i,e){return new RegExp(e).test(i)},validatePassword:function(i){return!(i.length<6)||(this.form.invalid("password"),this.form.invalidText($("[name=password]").data("ruleLimit").replace(/\d/,6)),!1)},submit:function(e){$.ajax({url:"/web/api/user/register",method:"POST",data:JSON.stringify(e),contentType:"application/json",dataType:"json"}).then(function(){i.location.href="/"}).fail(function(i){t.form.invalidFields(i.responseJSON.field),t.form.invalidText(i.responseJSON.message)})}};t.init()}(this,this.document);