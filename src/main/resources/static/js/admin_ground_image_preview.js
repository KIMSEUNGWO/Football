// 사진은 최대 4장까지만 처리
const maxImages = 4;

window.addEventListener('load', () => {

    let maxImage = document.querySelector('#maxImage');
    maxImage.innerHTML = '사진추가 (최대 ' + maxImages + '장)';
    

    let inputFile = document.querySelector('input[type="file"]');
    inputFile.addEventListener('change', (e) => {

        let files = inputFile.files;

        if (!isImage(files)) {
            inputFile.value = '';
            return;
        } 
        // 이미지 담을 배열 생성
        let imageFiles = getFileArray(files);

        // 최대개수를 넘으면 파일 개수 조정
        if (!isMaxLen(files)) {
            inputFile.files = newFileList(imageFiles);
        }

        printPreview(imageFiles);
    })

    let addImageBtn = document.querySelector('#addImage');
    addImageBtn.addEventListener('click', e => {
        inputFile.click();
    })

})

function newFileList(imageFiles) {
    // 새로운 DataTransfer 객체를 생성하고 파일을 추가
    let newFileList = new DataTransfer();
    imageFiles.forEach(function (file) {
        newFileList.items.add(file);
    });
    return newFileList.files;
}

function getFileArray(files) {
    let array = [];
    for (let i=0;i<Math.min(files.length, maxImages);i++){
        array.push(files[i]);
    }
    return array;
}

function printPreview(imageFiles) {
    clearPreview();
    let preview = document.querySelector('.preview');

    for (let i=0;i<imageFiles.length;i++){
        preview.innerHTML += createImgBox(imageFiles[i]);
    }

}

function createImgBox(file) {
    return '<div class="imgBox"><img src="' + URL.createObjectURL(file) + '" alt="' + file.name +'"/></div>'
}

function clearPreview() {
    let images = document.querySelectorAll('div.imgBox');
    images.forEach(image => {
        image.remove();
    })
}


function isMaxLen(files) {
    if (files.length <= maxImages) {
        return true;
    }
    alert('사진은 최대 ' + maxImages + '장까지 첨부할 수 있습니다.');
    return false;
}
function isImage(files) {
    for (let i=0;i<files.length;i++){
        let file = files[i];

        if (!String(file.type).startsWith('image/')){
            alert('사진만 추가할 수 있습니다.');
            return false;
        }
    }
    return true;
}