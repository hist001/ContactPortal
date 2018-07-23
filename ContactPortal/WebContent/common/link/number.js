
// 값이 널인지를 검사한다.JS
function isEmpty(s) 
{
  return ((s == null) || (s.lenght == 0 ))
}

//값이 숫자인지를 검사한다
function isDigit(c) 
{
  return ((c >= "0") && (c <= "9"))
}

//값이 정수인지를 검사한다.
function isInteger(s) 
{
   var i;
   
   if (isEmpty(s))
      return false
   
   for (i = 0; i < s.length; i++) {
      var c = s.charAt(i);
      if (!isDigit(c)) return false;
   }
   return true;
}

//부호가 있는 정수인지를 검사한다.
function isSignedInteger(s) 
{
    if (isEmpty(s))
        return false
    
    var startPos = 0 ;
    
    if ((s.charAt(0) == "-") || (s.charAt(0) == "+"))
       startPos = 1;
    return (isInteger(s.substring(startPos, s.length)))
}

//100평균을 넘지못하도록 한다.
function isNonnegativeInteger(s) 
{
  return ( isSignedInteger(s) && (parseInt(s) >= 0) && (parseInt(s) <= 100) );
}

//날자형식인지를 검사한다.
function isDate(s) 
{
//    alert("1:"+s);
    if (isEmpty(s))
        return false

//    alert("2:"+s);
    if (! (s.length == 10))
        return false

//    alert("3:"+s);
    if ((!(s.charAt(4) == "-")) || (!(s.charAt(7) == "-")))
        return false
        
//    alert("4:"+s);
    if ( !isInteger(s.substring(0, 4)) || !isInteger(s.substring(5, 7)) || !isInteger(s.substring(8, 10)) )
        return false

//    alert("5:"+s);
    if (! isFormatDate(s))
        return false

   return true
}

//YYYY-MM-DD의 형식인지를 검사한다.
function isFormatDate(s) 
{
//  alert("6:"+s);
  if ( isMMDate(s.substring(5, 7)) && isDDDate(s.substring(8, 10)) )
  return true
  else
  return false
}

//월입력시 해달월이 맞는지를 검사한다.
function isMMDate(s) 
{
//    alert("7:" + s +" int :" + parseInt(s));
    if (s.length > 1) {
      if (s.charAt(0) == 0) { s = s.substring(1, 2) }
    }
//    alert("7-1:" + s +" int :" + parseInt(s));
      
    return ((parseInt(s) > 0) && (parseInt(s) <= 12))
}

//일입력시 해당 일자가 날자를 넘는지 검사한다.
function isDDDate(s) 
{
//    alert("8:"+s +" int :" +parseInt(s));
    if (s.length > 1) {
      if (s.charAt(0) == 0) { s = s.substring(1, 2) }
    }
    return ((parseInt(s) > 0) && (parseInt(s) <= 31))
}
