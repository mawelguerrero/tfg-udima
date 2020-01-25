// General validation file

/*%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
                             C O N S T A N T S
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%*/

LETTERS = "����������abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
DIGITS = "0123456789";
ZIP_VALID = DIGITS;
PHONE_VALID = DIGITS;
MAIL_VALID = LETTERS + DIGITS + "._@-";
INVALID_FOR_TEXT = "!�\\\"$%&()=?�|@#`+*^[]{}��_<>:;";
DATE_VALID=DIGITS + "/";

VALCIF_CODE = "1";
VALNIF_CODE = "2";

/*%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
                             F U N C T I O N S
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%*/

// Checks that a given character string has any character other than spaces
function isFilled (str)
{
	for (c = 0; c < str.length; c++)
	{
		if (str.charAt(c) != " ")
		{
			return true;
		}
	}
	return false;
}

// Checks if all the characters of the given string 'str' are present in the
// char set 'valStr'
function validate (str, valStr)
{
	for (c = 0; c < str.length; c++)
	{
		if (valStr.indexOf (str.charAt (c)) == -1)
		return false;
	}
	return true;
}

// Checks that none of the characters in the given string 'str' are present in
// the char set 'valStr'.
// The name is a straight kick to the Webster's Dictionary, all right.
function unvalidate (str, valStr)
{
	for (c = 0; c < str.length; c++)
	{
		if (valStr.indexOf (str.charAt (c)) != -1)
		{
			return false;
		}
	}
	return true;
}

function validateAlphabeticStr (str, desc, compulsory)
{
	if (compulsory == true && !isFilled (str))
	{
		alert (desc + " es un campo obligatorio.");
		return false;
	}

	if (str.length > 0)
	{
		if (!unvalidate (str, INVALID_FOR_TEXT))
		{
			alert (desc + " sólo puede contener carácteres alfabéticos");
			return false;
		}
	}
	return true;
}

function validateNoStrictNb (str, desc, min, max)
{
	if (!validate (str, DIGITS))
	{
		alert (desc + " debe contener un número válido");
		return false;
	}
	nb = parseInt (str);
	if (nb < min)
	{
		alert (desc + " debe ser mayor o igual que " + min);
		return false;
	}
	if (nb > max)
	{
		alert (desc + " debe ser menor o igual que " + max);
		return false;
	}
	return true;
}

function validateNb (str, desc, min, max)
{
	if (!validate (str, DIGITS))
	{
		alert (desc + " debe contener un número válido");
		return false;
	}
	nb = parseInt (str);
	if (nb < min)
	{
		alert (desc + " debe ser mayor que " + min);
		return false;
	}
	if (nb > max)
	{
		alert (desc + " debe ser menor que " + max);
		return false;
	}
	return true;
}

function validateIntnlPhone (str, desc, compulsory)
{
	if (compulsory == true && !isFilled (str))
	{
		alert (desc + " es un campo obligatorio.");
		return false;
	}

	if (str.length < 9)
	{
			alert (desc + " es demasiado corto.");
			return false;
	}

	if (str.lenght > 15)
	{
			alert (desc + " es demasiado largo.");
			return false;
	}

	if (!validate (str, DIGITS))
	{
		alert (desc + " solo puede contener numeros");
		return false;
	}
	return true;
}

function validateMail (str, desc, compulsory)
{
	if (compulsory == true && !isFilled (str))
	{
		alert (desc + " es un campo obligatorio.");
		return false;
	}

	if (str.length > 0)
	{
		if (str.length < 5)
		{
			alert (desc + " es demasiado corta.");
			return false;
		}
		if (!validate (str, MAIL_VALID))
		{
			alert (desc + " no es válida.");
			return false;
		}
		if (str.indexOf ("@") == -1 || str.indexOf (".") == -1 || str.indexOf (" ") != -1 || str.charAt(str.length-1) == '.' || str.charAt(str.length-1) == '@')
		{
			alert (desc + " no es válida.");
			return false;
		}
	}
	return true;
}

function validatePhone (str, desc, compulsory)
{
	if (compulsory == true && !isFilled (str))
	{
		alert (desc + " es un campo obligatorio.");
		return false;
	}

	if (str.length > 0)
	{
		if (str.length < 9)
		{
			alert (desc + " es demasiado corto.");
			return false;
		}
		if (str.charAt (0) != '6' && str.charAt (0) != '9')
		{
			alert (desc + " es incorrecto.");
			return false;
		}
		if (!validate (str, PHONE_VALID))
		{
			alert (desc + " no es válido.");
			return false;
		}
		dashes = 0;
		for (i=0;i< str.length;i++)
		{
			if (str.charAt (i) == '-')
			{
				dashes++;
			}
		}
		if ((str.length - dashes) != 9)
		{
			alert (desc + " no es válido");
			return false;
		}
	}
	return true;
}

function validateFax (str, desc, compulsory)
{
	if (compulsory == true && !isFilled (str))
	{
		alert (desc + " es un campo obligatorio.");
		return false;
	}

	if (str.length > 0)
	{
		if (str.length < 9)
		{
			alert (desc + " es demasiado corto.");
			return false;
		}
		if (str.charAt (0) != '9')
		{
			alert (desc + " es incorrecto");
			return false;
		}
		if (!validate (str, PHONE_VALID))
		{
			alert (desc + " no es válido.");
			return false;
		}
		dashes = 0;
		for (i=0;i< str.length;i++)
		{
			if (str.charAt (i) == '-')
			{
				dashes++;
			}
		}
		if ((str.length - dashes) != 9)
		{
			alert (desc + " no es válido");
			return false;
		}
	}
	return true;
}

function isLeapYear (year)
{
	return ((((year%4)==0) && !((year%100)==0)) || (((year%4)==0) && ((year%400)==0)));
}

function isDateBeforeNow (str)
{
		day = parseFloat (str.substring (0, 2));
		month = parseFloat (str.substring (3, 5));
		year = parseFloat (str.substring (6, 10));
		curDate = new Date ();
		curYear = curDate.getYear() + 1900;
		curMonth = curDate.getMonth () + 1;
		if (curYear > year ||
		    curYear == year && curMonth > month ||
		    curYear == year && curMonth == month  && curDate.getDate () > day)
		{
			return true;
		}
		return false;
}

function validateDate (str, desc, compulsory, canBeInTheFuture)
{

        if (!isFilled (str) || str=='dd/mm/aaaa')
	{
		if (compulsory == true)
		{
			alert (desc + " es un campo obligatorio.");
			return false;
		}
		else
		{
			return true;
		}
	}

	if (str.length > 0)
	{
		if (!validate (str, DATE_VALID))
		{
			alert (desc + " no es válida.");
			return false;
		}
		compactedDate = str.substring (0, 2) + str.substring (3, 5) + str.substring (6, 10);
		if (!validate (compactedDate, DIGITS))
		{
			alert (desc + " no es válida.");
			return false;
		}
		day = parseFloat (str.substring (0, 2));
		month = parseFloat (str.substring (3, 5));
		year = parseFloat (str.substring (6, 10));
		REGULAR_MONTHS = new Array (31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31);
		LEAP_MONTHS = new Array (31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31);
		curDate = new Date ();

		if (!canBeInTheFuture)
		{
			curYear = curDate.getYear() + 1900;
			curMonth = curDate.getMonth () + 1;
//			alert ("current Date yyyy/mm/dd: " + curYear + "/" + curMonth + "/" + curDate.getDate ());
//			alert ("input Date yyyy/mm/dd: " + year + "/" + month + "/" + day);

			if (curYear < year ||
			    curYear == year && curMonth < month ||
			    curYear == year && curMonth == month  && curDate.getDate () < day)
			{
					alert (desc + " es posterior a la fecha actual.");
					return false;
			}
		}

		if (year < 1900)
		{
			alert (desc + " es inválida");
			return false;
		}
		if (month < 1 || month > 12)
		{
			alert (desc + " es inválida");
			return false;
		}
		if (isLeapYear (year))
		{
			if (day < 1 || day > LEAP_MONTHS[month-1])
			{
				alert (desc + " es inválida.");
				return false;
			}
		}
		else
		{
			if (day < 1 || day > REGULAR_MONTHS[month-1])
			{
				alert (desc + " es inválida.");
				return false;
			}
		}
	}
	return true;
}

function validateYear (str, desc, compulsory)
{
	if (compulsory == true && !isFilled (str))
	{
		alert (desc + " es un campo obligatorio.");
		return false;
	}

	if (str.length != 4)
	{
		alert (desc + " debe tener por 4 dígitos.");
		return false;
	}

	if (!validate (str, DIGITS))
	{
		alert (desc + " es inválido.");
		return false;
	}

	year = parseInt (str);
	curYear = new Date().getYear() + 1900;

	if (year < 1800)
	{
		alert (desc + " es demasiado antiguo");
		return false;
	}

	if (year > curYear)
	{
		alert (desc + " debe ser menor del año actual (" + curYear + ")");
		return false;
	}
	return true;
}

function validateNIF (nif, desc)
{
	numeros=nif.substring (0, nif.length-1);
	letra=nif.substring (nif.length-1).toUpperCase();
	if (!validate (numeros, DIGITS) || !validate (letra, LETTERS))
	{
		alert (desc + " no es correcto");
		return false;
	}
	ctrlLetters = new Array ("T", "R", "W", "A", "G", "M", "Y", "F", "P", "D", "X", "B", "N", "J", "Z", "S", "Q", "V", "H", "L", "C", "K", "E");
	index = numeros - (parseInt (numeros/23)*23);
	if (ctrlLetters[index] != letra)
	{
		alert (desc + " no es correcto");
		return false;
	}
	return true;
}

function validateCIF (cif, desc) {
	var letras = "ABCDEFGHPQS";
	var letras2 = "ABCDEFGHIJ";
	var numeros = "0123456789";

	// Comprobar la longitud
	if (cif.length != 9)
	{
		alert (desc + " no tiene una longitud correcta");
		return false;
	}

	// Comprobar la primera letra
	if (letras.indexOf (cif.toUpperCase().charAt (0)) == -1)
	{
		alert (desc + " no es correcto.");
		return false;
	}

	// Comprobar que el resto son numeros (salvo el ultimo)
	for (var n = 1; n < (cif.length - 1); n++)
	{
		if (numeros.indexOf (cif.charAt (n)) == -1)
		{
			alert (desc + " no es correcto.");
			return false;
		}
	}

	// Calcular los pesos
	var i1 = 2 * parseInt (cif.charAt (1));
	if (i1 > 9)
	{
		i1 = 1 + i1 % 10;
	}
	var i3 = 2 * parseInt (cif.charAt (3));
	if (i3 > 9)
	{
		i3 = 1 + i3 % 10;
	}
	var i5 = 2 * parseInt (cif.charAt (5));
	if (i5 > 9)
	{
		i5 = 1 + i5 % 10;
	}
	var i7 = 2 * parseInt (cif.charAt (7));
	if (i7 > 9)
	{
		i7 = 1 + i7 % 10;
	}

	// Sumar cifras y quedarnos con la ultima
	var suma = i1 + parseInt (cif.charAt (2)) +
	           i3 + parseInt (cif.charAt (4)) +
	           i5 + parseInt (cif.charAt (6)) +
	           i7;
	suma = suma.toString().charAt (suma.toString().length - 1);

	// Comprobar la letra o el digito de control
	var digito = 10 - suma;
	if (digito == 10)
	{
		digito = 0;
	}
	var letra = 10 - suma;
	if (letra == 0)
	{
		letra = 10;
	}
	var letraOk = letras2.toUpperCase().charAt (letra - 1);
	if ((cif.toUpperCase().charAt (8) == letraOk) || (cif.charAt (8) == digito))
	{
		return true;
	}

	alert (desc + " no es correcto");
	return false;
}

function validateDocument (str, docType, desc, compulsory)
{
	if (compulsory == true && !isFilled (str))
	{
		alert (desc + " es un campo obligatorio.");
		return false;
	}

	if (docType == VALCIF_CODE)
	{
		return validateCIF (str, desc);
	}
	else if (docType == VALNIF_CODE)
	{
		return validateNIF (str, desc);
	}
	alert (desc + " es inválido");
	return false;
}

function carga_cadena (el_string, el_array)
{
	for(c=0; c < el_string.length; c++)
	{
  	el_array[c] = parseFloat (el_string.substring(c, c + 1));
  }
}

function validateBankAccount (bank, agency, dc, acc)
{
	if (bank.length != 4)
	{
		alert ("El código del banco debe tener 4 dígitos.");
		return false;
	}
	if (agency.length != 4)
	{
		alert ("El código de la agencia bancaria debe tener 4 dígitos.");
		return false;
	}
	if (dc.length != 2)
	{
		alert ("Los dígitos de control de la cuanta bancaria deben ser 2.");
		return false;
	}

	zeroes = "0000000000";
	acc = zeroes.substring (0, acc.length-10) + acc;

	if (!validate (bank, DIGITS) || !validate (agency, DIGITS) ||
	    !validate (dc, DIGITS) || !validate (acc, DIGITS))
	{
		alert ("Los campos de una cuenta bancaria sólo pueden estar compuestos por dígitos.");
		return false;
	}
	if (acc == zeroes && bank == zeroes.substring(0,4) && agency == zeroes.substring(0,4) && dc == zeroes.substring(0,2))
	{
		alert ("La cuenta introducida no es válida.");
		return false;
	}

	Entidad = new Array(bank.length);
	Sucursal = new Array(agency.length);
	Digito = new Array(dc.length);
	Cuenta = new Array(acc.length);
	carga_cadena(bank,Entidad);
	carga_cadena(agency,Sucursal);
	carga_cadena(acc,Cuenta);
	var calculo1 = Entidad[0]*4 + Entidad[1]*8 + Entidad[2]*5 + Entidad[3]*10 + Sucursal[0]*9 + Sucursal[1]*7 + Sucursal[2]*3 + Sucursal[3]*6 ;
	var calculo2 = calculo1/11;
	var calculo3 = parseInt(calculo2);
	var calculo4 = calculo3 * 11;
	var calculo5 = calculo1 - calculo4;
	Digito1 = 11 - calculo5;
	var calculoa = Cuenta[0]*1 + Cuenta[1]*2 + Cuenta[2]*4 + Cuenta[3]*8 + Cuenta[4]*5 + Cuenta[5]*10 + Cuenta[6]*9 + Cuenta[7]*7 + Cuenta[8]*3 + Cuenta[9]*6;
	var calculob = calculoa/11;
	var calculoc = parseInt(calculob);
	var calculod = calculoc * 11;
	var calculoe = calculoa - calculod;
	Digito2 = 11 - calculoe;
	if (Digito1 ==10) Digito1=1;
	if (Digito1 ==11) Digito1=0;
	if (Digito2 ==10) Digito2=1;
	if (Digito2 ==11) Digito2=0;
	carga_cadena(dc,Digito);
	if(!((Digito[0] == Digito1) && (Digito[1] == Digito2)))
	{
		alert ("La cuenta bancaria no es válida.");
  	return false;
	}
  return true;
}


function showAlert(strElementName, strMessage) {
        alert("El campo " + strElementName + strMessage);
}

/*  comprueba si un elemento es nulo */
function checkNotNull(objElement) {
        if ( (objElement.value == null) || (objElement.value == "") ) return false;
        return true;
}

/*	valida que un elemento no sea nulo */
function validateNotNull(objElement, strElementName) {
        if ( !checkNotNull(objElement) || objElement.value=="" ) {
                showAlert(strElementName, " es obligatorio");
                objElement.focus();
                return false;
        }
        return true;
}

function validateMandatoryFields(formFields){


        for(i=0; i<formFields.length; i++){
                var field = formFields[i];
                var element = document.forms['0'][field[0]];
                if(!validateNotNull(element, field[1])){
                        return false;
                }
        }
        return true;
}

function y2k(number) {
	return (number < 1000) ? number + 1900 : number;
}

function isValidDate(myDate,sep) {
	if (myDate.length == 10) {
        	if (myDate.substring(2,3) == sep && myDate.substring(5,6) == sep) {
                	var date  = myDate.substring(0,2);
                	var month = myDate.substring(3,5);
                	var year  = myDate.substring(6,10);
                	var test = new Date(year,month-1,date);
                	var today = new Date();

                	if (today < test) {
                  		return false;
                	}
                	if (year == y2k(test.getYear()) && (month-1 == test.getMonth()) && (date == test.getDate())) {
                  		reason = '';
                  		return true;
                        }
                	else {
                  		reason = 'valid format but an invalid date';
                  		return false;
                	}
              	}
              	else {
                	reason = 'invalid spearators';
                	return false;
              	}
	}
        else {
        	reason = 'invalid length';
              	return false;
        }
	alert(reason);
}

