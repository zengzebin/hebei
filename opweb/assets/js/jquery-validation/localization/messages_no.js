!function(e){"function"==typeof define&&define.amd?define(["jquery","../jquery.validate"],e):"object"==typeof module&&module.exports?module.exports=e(require("jquery")):e(jQuery)}(function(e){e.extend(e.validator.messages,{required:"Dette feltet er obligatorisk.",maxlength:e.validator.format("Maksimalt {0} tegn."),minlength:e.validator.format("Minimum {0} tegn."),rangelength:e.validator.format("Angi minimum {0} og maksimum {1} tegn."),email:"Oppgi en gyldig epostadresse.",url:"Angi en gyldig URL.",date:"Angi en gyldig dato.",dateISO:"Angi en gyldig dato (&ARING;&ARING;&ARING;&ARING;-MM-DD).",dateSE:"Angi en gyldig dato.",number:"Angi et gyldig nummer.",numberSE:"Angi et gyldig nummer.",digits:"Skriv kun tall.",equalTo:"Skriv samme verdi igjen.",range:e.validator.format("Angi en verdi mellom {0} og {1}."),max:e.validator.format("Angi en verdi som er mindre eller lik {0}."),min:e.validator.format("Angi en verdi som er st&oslash;rre eller lik {0}."),creditcard:"Angi et gyldig kredittkortnummer."})});