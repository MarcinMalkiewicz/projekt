

document.addEventListener("DOMContentLoaded",()=>{
	
	(async()=>{
		const response = await fetch("/projects/"); 
		const json = await response.json();
		console.table(json); //nazwa
		json.projects.forEach((project)=>{
		
		const main = document.getElementsByTagName('main')[0];
		const div = document.createElement('div');
		const div1 = document.createElement('div');
		const div2 = document.createElement('div');
		const p = document.createElement('p')
		const a = document.createElement('a')
		const img = document.createElement('img')
		const h4 = document.createElement('h4')
		
		div.setAttribute('class','course item');
		div1.setAttribute('class','course-miniature');
		div2.setAttribute('class','course-description');
		p.setAttribute('class','course-author');
		
		a.href = '/projects/lessons/'+ project.name;
		p.innerHTML= project.owner;
		h4.innerHTML = project.description;
		//div1.appendChild(a);
		div1.appendChild(img);
		div2.appendChild(p);
		div2.appendChild(h4);
		div2.innerHTML= project.name;
		div.appendChild(div1);
		div.appendChild(div2);
		a.appendChild(div);
		main.appendChild(a);
		
		
		
		
		});
		
		
		
	})();
});