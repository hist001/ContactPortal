    		function getVal1(){
               var value = $("#test10 option:selected").val();
               var text = $("#test10 option:selected").text();
               //alert(value + " :: " + text);
               if(value){
            	   $("#test20").show();
               }
               $("#I_CODE").val(value);
               //alert($("#I_CODE").val());
           }
            function getVal2(){
               var value = $("#test20 option:selected").val();
               var text = $("#test20 option:selected").text();
               //alert(value + " :: " + text);
               if(value){
            	   $("#test30").show();
               }
               $("#I_CODE").val(value);
               //alert($("#I_CODE").val());
           }
           function getVal3(){
               var value = $("#test30 option:selected").val();
               var text = $("#test30 option:selected").text();
               //alert(value + " :: " + text);
               if(value){
            	   $("#test40").show();
               }
               $("#I_CODE").val(value);
              // alert($("#I_CODE").val());
           }
           function getVal4(){
               var value = $("#test40 option:selected").val();
               var text = $("#test40 option:selected").text();
               
               //alert(value + " :: " + text);
               $("#I_CODE").val(value);
               alert($("#I_CODE").val());
               //alert($("#I_CODE").val());
           }
           
           //위치 코드 가져오기
           function getVal5(){
               var value = $("#test50 option:selected").val();
               var text = $("#test50 option:selected").text();
               //alert(value + " :: " + text);             
               $("#L_CODE").val(value);
               alert($("#L_CODE").val());
           }
            function getVal6(){
               var value = $("#test60 option:selected").val();
               var text = $("#test60 option:selected").text();
               //alert(value + " :: " + text);
               $("#L_CODE").val(value);
              // alert($("#L_CODE").val());
           }
           function getVal7(){
               var value = $("#test70 option:selected").val();
               var text = $("#test70 option:selected").text();
               //alert(value + " :: " + text);
               $("#L_CODE").val(value);
               //alert($("#L_CODE").val());
           }
           
         $(document).ready(function(){
        	 
        	 $("#test10").on("change", function(){
                 getVal1();
             	  });
         	$("#test20").on("change", function(){
         		getVal2();
            });
            $("#test30").on("change", function(){
            	getVal3();
            });
            $("#test40").on("change", function(){
               getVal4();
            }); 
            
            $("#test50").on("change", function(){
                getVal5()
                });
            $("#test60").on("change", function(){
                getVal6();
             });
             $("#test70").on("change", function(){
                getVal7();
             }); 
		});