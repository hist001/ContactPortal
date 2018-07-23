function printPopup()
{
    var html = "";
    for(var i = 1; i < 10; i++)
    {
        var frm = document.getElementById("printArea" + i);
        if(frm)
        {
            html += frm.innerHTML;
            html += "<br>";

        }
        else
        {
            break;
        }
    }

    var frm = document.forms["printForm"];

    frm.elements["content"].value=escape(html);

    popWindow = window.open("", "printWindow", "width=750,height=500,toolbar=0,location=0,directories=0,status=0,menubar=0,scrollbars=1,resizable=1");

    frm.target = "printWindow";
    frm.submit();
}