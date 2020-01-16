import React from "react";
import axios from 'axios'
import {number} from "prop-types";


const key = "dict.1.1.20191117T144211Z.52a5aff6f80405cd.c3679c5319752f9de47c264bd000cee692ab96aa";
const url = "https://dictionary.yandex.net/api/v1/dicservice.json/lookup?key="
    + key + "&lang=en-en" + "&text=";

class SynonymTooltip extends React.Component{

    constructor(props){
        super(props);
        this.state = {display: 'none', x: 0, y:0, synonyms:[]};
    }
    handleSelect = async () => {
            let data = this.getTextAndRect();
            let syn;
            if (data.text !== "") {
                let response = await axios.get(url + data.text, {});
                if (response.data.def.length == 0){
                    syn = [];
                }else{
                    console.log(response)
                    syn = response.data.def[0].tr.map((x) => x.text);
                }
                this.state.visibility = true;
                this.setState({display: 'inherit', x: data.rect.left, y: (data.rect.top + data.rect.height + 4), synonyms: syn});
            } else {
                this.setState({display: 'none', x: 0, y: 0});
            }

    };

    getTextAndRect = () => {
        let text, rect;
        const elem = document.activeElement;
        if(elem.tagName === "TEXTAREA" ||
            (elem.tagName === "INPUT" && elem.type === "text")) {
            text = elem.value.substring(elem.selectionStart, elem.selectionEnd);
            rect = elem.getBoundingClientRect();

        } else if (window.getSelection() && window.getSelection().toString() !== ""){
            rect = window.getSelection().getRangeAt(0).getBoundingClientRect();
            text = window.getSelection().toString();
        } else {
            text = "";
            rect = null;
        }
        return {text:text, rect:rect}
    };

    handle

    componentDidMount() {
        document.addEventListener('mouseup', this.handleSelect);
        document.addEventListener('dblclick', this.handleSelect);
    }

    componentWillUnmount() {
        document.removeEventListener('mouseup', this.handleSelect);
        document.removeEventListener('dblclick', this.handleSelect);
    }
    render() {
        const tab = this.state.synonyms;
        let style = {
            display: this.state.display,
            position :'absolute',
            backgroundColor:"white",
            top: ((this.state.y + window.scrollY) + 'px'), //(window.scrollY + rect.top + rect.height) + 'px';
            left: ((this.state.x + window.scrollX) + 'px'),
            zIndex: 10,
            textAlign: "center",
            opacity: 0.9
        };
        let synList;
        if (tab.length > 0) {
            synList = tab.map((value, index) =>
                <tr>
                    <td key={index}>
                        {index < 10 ? value : null}
                    </td>
                </tr>);
        } else {
            synList = <tr>
                <td>
                    ---
                </td>
            </tr>
        }

            return <div style={style}>
                <table>
                    <tr>
                        <th>Synonyms</th>
                    </tr>
                    {synList}
                </table>
            </div>

    }
}

export default SynonymTooltip;