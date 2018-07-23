function getCookie( name ){
        var nameOfCookie = name + "=";
        var x = 0;
        while ( x <= document.cookie.length )
        {
                var y = (x+nameOfCookie.length);
                if ( document.cookie.substring( x, y ) == nameOfCookie ) {
                        if ( (endOfCookie=document.cookie.indexOf( ";", y )) == -1 )
                                endOfCookie = document.cookie.length;
                        return unescape( document.cookie.substring( y, endOfCookie ) );
                }
                x = document.cookie.indexOf( " ", x ) + 1;
                if ( x == 0 )
                        break;
        }
        return "";
}

// 팝업창에서 만들어진 쿠키 Notice 의 값이 done 이 아니면(즉, 체크하지 않으면,) 
// 공지창 (new.htm) 을 띄웁니다

if ( getCookie( "Notice" ) != "done" ) {
        window.open('/popup.htm','notice','left=0, top=0, width=510,height=505');
}

