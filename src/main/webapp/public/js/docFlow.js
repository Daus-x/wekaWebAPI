$(document).ready(function() {
    $.ajax({
        url: "/files",
		method: "get"
    }).then(function(data) {
        $('#trainFiles ul').empty();
        var list='';
        for(var i=0;i<data.length;i++){
            list+='<li ><a href="#" data-listener="click" data-action="selectTrainFile" data-name="'+data[i]+'">'+data[i]+'</a></li>';
        }
        $('#trainFiles').html(list);
    });
});
jQuery(
	function(){

	function convertFormArrayToObject(formArray) {
        var postData = {};
        for(var i = 0, length = formArray.length; i < length; i++) {
            var field = formArray[i];
            postData[field.name] = field.value;
        }
        return postData;
    }
		
        trainFile='';
        testFile='';
		var clickActions = {
            "trainOptions": function(event) {
			var list='';
			$("#testFilesDiv").removeClass("hidden");
			$("#submitDiv").removeClass("col-lg-12");
			$("#submitDiv").addClass("col-lg-6");
			$('#testFiles ul').empty();

                $.ajax({
                    url: "/files",
                    method: "get"
                }).then(function(data) {
                    $('#trainFiles ul').empty();
                    var list='';
                    for(var i=0;i<data.length;i++){
                        list+='<li><a href="#" data-listener="click" data-action="selectTestFile" data-name="'+data[i]+'">'+data[i]+'</a></li>';
                    }
                    $('#testFiles').html(list);
                });
			
			$("#submitBtn").attr("data-action","submitTrain");
	
			},
		    "crossValidateOptions": function(event) {
			$("#testFilesDiv").addClass("hidden");
			$("#submitDiv").removeClass("col-lg-6");
			$("#submitDiv").addClass("col-lg-12");
			
			$("#submitBtn").attr("data-action","submitCrossvalidate");
	
			},
            "selectTestFile":function(event){
                var target=event.target;
                testFile=jQuery(target).attr("data-name");
            },
            "selectTrainFile":function(event){
                var target=event.target;
                trainFile=jQuery(target).attr("data-name");


                $.ajax({
                    url: "/files/"+trainFile+"/",
                    method: "get"
                }).then(function(data) {
                    $('#attributes ul').empty();
                    var list='';
                    for(var i=0;i<data.length;i++){
                        list+='<li class="list-group-item">'+data[i]+'</li>';
                    }
                    $('#attributes').html(list);
                });
            },
            "submitTrain":function(event){
                var target=event.target;

                $.ajax({
                    url: "/train/"+trainFile+"/"+testFile+"/",
                    method: "get"
                }).then(function(data) {
                    $('#results ul').empty();
                    var list='';
                    for(var i=0;i<data.length;i++){
                        list+='<li class="list-group-item">'+data[i]+'</li>';
                    }
                    $('#results').html(list);
                });
            },
            "submitCrossvalidate":function(event){
                $.ajax({
                    url: "/cross-validate/"+trainFile+"/",
                    method: "get"
                }).then(function(data) {
                    $('#results ul').empty();
                    var list='';
                    for(var i=0;i<data.length;i++){
                        list+='<li class="list-group-item">'+data[i]+'</li>';
                    }
                    $('#results').html(list);
                });
            }

		};
		
		var submitActions = {
        "/channel":
            function(event){
				
				
			}
		};

		jQuery("body").on("click", "[data-listener=click]", function(event) {
            var target = event.target;
            var action = jQuery(target).attr("data-action");
            if(action != undefined) {
                clickActions[action](event);
            }
        });


        jQuery("body").on("submit", "form", function(event) {
            var target = event.target;
            var action = jQuery(target).attr("action");
            // console.log(event);
            //debugger;
            if(submitActions[action] != undefined) {
                submitActions[action](event);
            }
        });
});