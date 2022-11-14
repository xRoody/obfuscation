package com.example.obfuscation;

import com.example.obfuscation.logic.Obfuscator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class ObfuscationApplication {

    public static void main(String[] args) {
        ApplicationContext context=SpringApplication.run(ObfuscationApplication.class, args);
        Obfuscator obfuscator=context.getBean(Obfuscator.class);
        System.out.println(obfuscator.obfuscate("const myStorage = window.localStorage;\n" +
                "const search = document.getElementsByClassName(\"search_input\")[0];\n" +
                "const form = document.getElementsByClassName(\"form\")[0];\n" +
                "const pattern=\"https://api.themoviedb.org/3/discover/movie?api_key=3fd2be6f0c70a2a598f084ddfb75487c&page=1\";\n" +
                "let url = \"https://api.themoviedb.org/3/discover/movie?api_key=3fd2be6f0c70a2a598f084ddfb75487c&page=1\";\n" +
                "let url2 = \"https://api.themoviedb.org/3/movie/{id}?api_key=3fd2be6f0c70a2a598f084ddfb75487c\";\n" +
                "let main = document.getElementById(\"main\");\n" +
                "const body = document.getElementsByTagName(\"body\")[0];\n" +
                "const button = document.getElementById(\"search_button\");\n" +
                "const input = document.getElementById(\"search_input\");\n" +
                "const reset=document.getElementsByClassName(\"reset\")[0];\n" +
                "const genre=document.getElementById(\"genre\");\n" +
                "const year=document.getElementById(\"range\");\n" +
                "const year_text=document.getElementById(\"year\");\n" +
                "const fav=document.getElementById(\"fav\");\n" +
                "let page=1;\n" +
                "let isFavOpen=false;\n" +
                "document.addEventListener('keypress', (event)=>{\n" +
                "    if(event.code=='Enter' && input.value) {\n" +
                "        button.click();\n" +
                "    }\n" +
                "})\n" +
                "reset.addEventListener(\"click\",()=>{\n" +
                "    input.value=\"\";\n" +
                "    reset.classList.remove(\"reset_on\");\n" +
                "})\n" +
                "search.addEventListener(\"input\",()=>{\n" +
                "    if(input.value) reset.classList.add(\"reset_on\");\n" +
                "    else reset.classList.remove(\"reset_on\");\n" +
                "})\n" +
                "search.addEventListener(\"focusin\", () => {\n" +
                "    form.classList.toggle(\"form_shadow\");\n" +
                "})\n" +
                "search.addEventListener(\"focusout\", () => {\n" +
                "    form.classList.toggle(\"form_shadow\");\n" +
                "})\n" +
                "document.addEventListener(\"scroll\", () => {\n" +
                "    let marker = document.getElementsByClassName(\"bottom_marker\");\n" +
                "    if (marker.length > 0 ? marker[0].getBoundingClientRect().top <= document.documentElement.clientHeight : false) {\n" +
                "        let marker = document.getElementsByClassName(\"bottom_marker\");\n" +
                "        marker[0].classList.remove(\"bottom_marker\");\n" +
                "        lazyShowData();\n" +
                "    }\n" +
                "})\n" +
                "button.addEventListener(\"click\", async ()=>{\n" +
                "        removeAll();\n" +
                "        isFavOpen=false;\n" +
                "        let ind=pattern.indexOf(\"api_key\")+41;\n" +
                "        let url1=pattern.substr(0,ind-1);\n" +
                "        if(genre.value && genre.value.trim()){\n" +
                "            const res = await fetch(\"https://api.themoviedb.org/3/genre/list?api_key=3fd2be6f0c70a2a598f084ddfb75487c\");\n" +
                "            const data = await res.json();\n" +
                "            let a=data.genres;\n" +
                "            let b=12;\n" +
                "            for(let el of a){\n" +
                "                if(genre.value.toLowerCase()===el.name.toLowerCase()) b=el.id;\n" +
                "            }\n" +
                "            url1=url1.concat(\"&with_genres=\"+b);\n" +
                "        }\n" +
                "        let y=Number(year.value)+1;\n" +
                "        url1=url1.concat(\"&primary_release_date.gte=\"+1960+\"-01-01\"+\"&primary_release_date.lte=\"+y+\"-01-01\");\n" +
                "        if (input.value.trim()){\n" +
                "            url1=url1.substring(0, url1.indexOf(\"discover\")).concat(\"search\").concat(url1.substring(url1.indexOf(\"/movie\"))).concat(\"&query=\"+input.value.toLowerCase());\n" +
                "            console.log(url1);\n" +
                "        }\n" +
                "        url1=url1.concat(\"&page=1\");\n" +
                "        page=1;\n" +
                "        url=url1;\n" +
                "        let arr=await getData(url1);\n" +
                "        showData(arr);\n" +
                "})\n" +
                "year.addEventListener(\"input\",()=>{\n" +
                "    year_text.textContent=year.value;\n" +
                "})\n" +
                "fav.addEventListener(\"click\",async ()=>{\n" +
                "    removeAll();\n" +
                "    isFavOpen=true;\n" +
                "    let arr=JSON.parse(myStorage.getItem(\"ids\"));\n" +
                "    let prefix=url2.substring(0, url2.indexOf(\"{id}\"));\n" +
                "    let suffix=url2.substring(url2.indexOf(\"{id}\")+4);\n" +
                "    let result=[];\n" +
                "    for(let id of arr){\n" +
                "        const res = await fetch(prefix+id+suffix);\n" +
                "        const data = await res.json();\n" +
                "        result.push(data);\n" +
                "    }\n" +
                "    showData(result);\n" +
                "})\n" +
                "async function getData(url) {\n" +
                "    const res = await fetch(url);\n" +
                "    const data = await res.json();\n" +
                "    return data.results;\n" +
                "}\n" +
                "    prepare();\n" +
                "async function prepare(){\n" +
                "    let resArr=await getData(url);\n" +
                "    showData(resArr);\n" +
                "    const a=[];\n" +
                "    if (!myStorage.getItem(\"ids\")) myStorage.setItem(\"ids\",JSON.stringify(a));\n" +
                "}\n" +
                "\n" +
                "function removeAll() {\n" +
                "    while (main.firstChild) {\n" +
                "        main.firstChild.remove();\n" +
                "    }\n" +
                "}\n" +
                "\n" +
                "async function lazyAddData() {\n" +
                "    page++;\n" +
                "    let url1=url.substr(0,url.length-1)+page;\n" +
                "    return getData(url1);\n" +
                "}\n" +
                "\n" +
                "async function lazyShowData(){\n" +
                "    let arr=await lazyAddData();\n" +
                "    console.log(arr);\n" +
                "    showData(arr);\n" +
                "}\n" +
                "\n" +
                "async function showData(arr){\n" +
                "    for(let i=0; i<arr.length; i++){\n" +
                "        let button=document.createElement(\"button\");\n" +
                "        button.classList.add(\"add_button\");\n" +
                "        button.value=arr[i].id;\n" +
                "        button.textContent=JSON.parse(myStorage.getItem(\"ids\")).includes(String(arr[i].id))?\"-\":\"+\";\n" +
                "        button.addEventListener(\"click\",(e)=>{\n" +
                "            let arr=JSON.parse(myStorage.getItem(\"ids\"));\n" +
                "            if(!arr.includes(e.target.value)){\n" +
                "                arr.push(e.target.value);\n" +
                "                button.textContent=\"-\";\n" +
                "            } else if(isFavOpen) {\n" +
                "                arr.splice(arr.indexOf(e.target.value), 1);\n" +
                "                main.removeChild(button.parentNode.parentNode);\n" +
                "            }else{\n" +
                "                arr.splice(arr.indexOf(e.target.value), 1);\n" +
                "                button.textContent=\"+\";\n" +
                "            }\n" +
                "            myStorage.setItem(\"ids\", JSON.stringify(arr));\n" +
                "            console.log(JSON.parse(myStorage.getItem(\"ids\")));\n" +
                "        })\n" +
                "        let rating=document.createElement(\"span\");\n" +
                "        if (arr[i].vote_average<3){\n" +
                "            rating.classList.add(\"bad\");\n" +
                "        }else if(arr[i].vote_average<6){\n" +
                "            rating.classList.add(\"middle\");\n" +
                "        }else{\n" +
                "            rating.classList.add(\"good\");\n" +
                "        }\n" +
                "        rating.textContent=arr[i].vote_average;\n" +
                "        let wr=document.createElement(\"div\");\n" +
                "        wr.classList.add(\"wr_style\");\n" +
                "        wr.appendChild(button);\n" +
                "        wr.appendChild(rating);\n" +
                "        let p=document.createElement(\"p\");\n" +
                "        p.classList.add(\"name\");\n" +
                "        p.textContent=arr[i].title;\n" +
                "        let img=document.createElement(\"img\");\n" +
                "        img.classList.add(\"preview\");\n" +
                "        img.src=\"https://image.tmdb.org/t/p/original/\"+arr[i].poster_path;\n" +
                "        let description=document.createElement(\"span\");\n" +
                "        description.classList.add(\"description\");\n" +
                "        description.textContent=arr[i].overview;\n" +
                "        let wrapperDiv=document.createElement(\"div\");\n" +
                "        wrapperDiv.classList.add(\"film_block\");\n" +
                "        wrapperDiv.appendChild(img);\n" +
                "        wrapperDiv.appendChild(description);\n" +
                "        wrapperDiv.appendChild(p);\n" +
                "        wrapperDiv.appendChild(wr);\n" +
                "        if (i===18){\n" +
                "            wrapperDiv.classList.add(\"bottom_marker\");\n" +
                "            console.log(1111);\n" +
                "        }\n" +
                "        main.appendChild(wrapperDiv);\n" +
                "    }\n" +
                "}\n"));
    }
}
