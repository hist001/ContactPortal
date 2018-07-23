
// ���� �������� �˻��Ѵ�.JS
function isEmpty(s) 
{
  return ((s == null) || (s.lenght == 0 ))
}

//���� ���������� �˻��Ѵ�
function isDigit(c) 
{
  return ((c >= "0") && (c <= "9"))
}

//���� ���������� �˻��Ѵ�.
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

//��ȣ�� �ִ� ���������� �˻��Ѵ�.
function isSignedInteger(s) 
{
    if (isEmpty(s))
        return false
    
    var startPos = 0 ;
    
    if ((s.charAt(0) == "-") || (s.charAt(0) == "+"))
       startPos = 1;
    return (isInteger(s.substring(startPos, s.length)))
}

//100����� �������ϵ��� �Ѵ�.
function isNonnegativeInteger(s) 
{
  return ( isSignedInteger(s) && (parseInt(s) >= 0) && (parseInt(s) <= 100) );
}

//�������������� �˻��Ѵ�.
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

//YYYY-MM-DD�� ���������� �˻��Ѵ�.
function isFormatDate(s) 
{
//  alert("6:"+s);
  if ( isMMDate(s.substring(5, 7)) && isDDDate(s.substring(8, 10)) )
  return true
  else
  return false
}

//���Է½� �ش޿��� �´����� �˻��Ѵ�.
function isMMDate(s) 
{
//    alert("7:" + s +" int :" + parseInt(s));
    if (s.length > 1) {
      if (s.charAt(0) == 0) { s = s.substring(1, 2) }
    }
//    alert("7-1:" + s +" int :" + parseInt(s));
      
    return ((parseInt(s) > 0) && (parseInt(s) <= 12))
}

//���Է½� �ش� ���ڰ� ���ڸ� �Ѵ��� �˻��Ѵ�.
function isDDDate(s) 
{
//    alert("8:"+s +" int :" +parseInt(s));
    if (s.length > 1) {
      if (s.charAt(0) == 0) { s = s.substring(1, 2) }
    }
    return ((parseInt(s) > 0) && (parseInt(s) <= 31))
}
