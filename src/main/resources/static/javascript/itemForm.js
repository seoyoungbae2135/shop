//itemForm.js 20240226-6

function bindImg(){
    $(".cs-file-input").on("change",function(){
        var fileName = $(this).val().split("\\").pop(); //이미지이름
        var fileExt = filename.substring(filename.lastIndex(".")+1); //확장자
        fileExt = fileExt.toLowerCase(); //소문자변환

        if( fileExt !="jpg" && fileExt !="jpeg" && fileExt != "png"
        && fileExt !="gif" && fileExt != "bmp"){
            alert("jpg, lpeg, png, gif, bmp 파일만 등록가능 합니다.")
            return;
        }
        $(this).prev(".input-group-text").html(fileName);
    });
}