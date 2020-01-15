import React from "react";
import {checkSynonyms} from "./synonyms";

class SynonymTooltip extends React.Component{
    constructor(props){
        super(props);
        this.state = {display: 'none', x: 0, y:0, synonyms:[]};
    }
    handleSelect = () => {
            let data = this.getTextAndRect();

            if (data.text !== "") {
                let syn = checkSynonyms(data.text);
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
                        {value}
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