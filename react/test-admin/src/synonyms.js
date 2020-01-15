/*
function takes parameter iput, which should look like this
{
	"text":"[word to check]"
  "lang":"[language of choseen word]"
}
and return Array of strings stringified to JSON
*/
export function checkSynonyms(text){
    let result = getSynonyms(text, "en");
    if(!Array.isArray(result)){
        result = getSynonyms(text,"pl");
    }
    if (!Array.isArray(result)){
        return [];
    } else {
        return result;
    }
}

function getSynonyms(text, lang){
    let result = undefined;
    const inputObj = extractTranslations(text, lang);
    if (inputObj !== null && inputObj.translations !== undefined){
        let i = -1;
        while(inputObj.translations.tr[++i] !== undefined){
            const output = request(inputObj.translations.tr[i].text, inputObj.lang);
            const syn = extractSyn(output, inputObj.word);
            if (syn != null){
                result = syn;
                break;
            }
        }
    }
    return result;
}

function extractTranslations(text, lang){
    if(text === undefined || lang === undefined){
        return null;
    }
    let word = text;
    word = word.trim().split(" ")[0];
    const response = request(word, lang==="en"?"en-en":"pl-ru");
    if(response == null){
        return null;
    }
    return {translations:JSON.parse(response).def[0],
        word: word,
        lang:lang==="en"?"en-en":"ru-pl"};
}

function request(word, lang){
    const key = "dict.1.1.20191117T144211Z.52a5aff6f80405cd.c3679c5319752f9de47c264bd000cee692ab96aa";
    const xhttp = new XMLHttpRequest();
    let output = null;
    xhttp.onreadystatechange = function() {
        if (this.readyState === 4 && this.status === 200) {
            output = this.responseText;
        }
    };
    const url = "https://dictionary.yandex.net/api/v1/dicservice.json/lookup?key="
        + key + "&lang=" + lang + "&text=" + word;
    xhttp.open("GET", url, false);
    xhttp.send();
    return output;
}

function extractSyn(input, originalWord) {
    const data = JSON.parse(input).def[0];
    if(data === undefined)
        return undefined;
    const tr = JSON.parse(input).def[0].tr;
    let tab2;
    let result = undefined;
    for (let j = 0; j < tr.length; j++) {
        if (tr[j].text === originalWord && tr[j].syn !== undefined) {
            tab2 = tr[j].syn;
            break;
        }
    }
    if (tab2 !== undefined) {
        result = [];
        for (let i = 0; i < tab2.length; i++) {
            result.push(tab2[i].text);
        }
        return result;
    }
}