
document.addEventListener("DOMContentLoaded",()=>{
	
	(async ()=>{
		const array = window.location.pathname.split("/");
		const zmienna = array[array.length-1];
		const response = await fetch("/projects/"+zmienna); 
		console.log(zmienna);
		const json = await response.json();
		const main = document.getElementsByTagName("main")[0];
		console.log(json);
	    json.lessons.forEach(async (lesson)=>{
	    	let div = document.createElement('div');
	    	let title = document.createElement('h3');
	    	let link = document.createElement('a');
	    	let img  = document.createElement('img');
	    	let description = document.createElement("p");
            const req = await fetch(`/projects/${json.name}/${lesson}`);
            const res = await req.json();
            console.log(res);
            link.href= `/projects/${json.name}/${res.name}/html`;
            title.innerHTML = res.name;
            description.innerHTML = res.description;
	    	div.appendChild(title);
	    	link.appendChild(div);
	    	main.appendChild(link);

	    });
		
		
})(); 
});