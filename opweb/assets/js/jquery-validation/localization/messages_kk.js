!function(e){"function"==typeof define&&define.amd?define(["jquery","../jquery.validate"],e):"object"==typeof module&&module.exports?module.exports=e(require("jquery")):e(jQuery)}(function(e){e.extend(e.validator.messages,{required:"Бұл өрісті міндетті түрде толтырыңыз.",remote:"Дұрыс мағына енгізуіңізді сұраймыз.",email:"Нақты электронды поштаңызды енгізуіңізді сұраймыз.",url:"Нақты URL-ды енгізуіңізді сұраймыз.",date:"Нақты URL-ды енгізуіңізді сұраймыз.",dateISO:"Нақты ISO форматымен сәйкес датасын енгізуіңізді сұраймыз.",number:"Күнді енгізуіңізді сұраймыз.",digits:"Тек қана сандарды енгізуіңізді сұраймыз.",creditcard:"Несие картасының нөмірін дұрыс енгізуіңізді сұраймыз.",equalTo:"Осы мәнді қайта енгізуіңізді сұраймыз.",extension:"Файлдың кеңейтуін дұрыс таңдаңыз.",maxlength:e.validator.format("Ұзындығы {0} символдан көр болмасын."),minlength:e.validator.format("Ұзындығы {0} символдан аз болмасын."),rangelength:e.validator.format("Ұзындығы {0}-{1} дейін мән енгізуіңізді сұраймыз."),range:e.validator.format("Пожалуйста, введите число от {0} до {1}. - {0} - {1} санын енгізуіңізді сұраймыз."),max:e.validator.format("{0} аз немесе тең санын енгізуіңіді сұраймыз."),min:e.validator.format("{0} көп немесе тең санын енгізуіңізді сұраймыз.")})});