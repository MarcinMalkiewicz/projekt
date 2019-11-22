
document.addEventListener("DOMContentLoaded",()=>{
	
	(async()=>{
		const array = window.location.pathname.split("/);
		const zmienna = array[array.length];
		const zmienna1 = array[array.length-1];
		
		const response = await fetch("/projects/"+zmienna1+'/' + zmienna); 
		const json = await response.json();
		json.lessons.forEach((lesson)=>{
			
			
		const main = document.getElementsByTagName('main')[0];
		const div = document.createElement('div');
		const div1 = document.createElement('div');
		const div2 = document.createElement('div');
		const h4 = document.createElement('h4')
		const h3 = document.createElement('h3')
		const video = document.createElement('video');
		
		div.setAttribute('class','course item');
		div1.setAttribute('class','course-miniature');
		div2.setAttribute('class','course-description');
		video.setAttribute('id' , 'video');
		
		main.appendChild(div);
		div.appendChild(div1);
		div.appendChild(div2);
		
		h3.innerHTML= lesson.projectname;
		h4.innerHTML= lesson.description;
		
		div1.appendChild(video);
		div2.appendChild(h3);
		div2.appendChild(h4);
		
		
		video.src=lesson.video;

		
	})();
});