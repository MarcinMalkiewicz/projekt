document.addEventListener("DOMContentLoaded",()=>{

	(async()=>{
		const response = await fetch("/users/getAll");
		const json = await response.json();
		console.log(json);
		json.users.forEach((userUser)=>{
		const main = document.getElementsByTagName('main')[0];
		const div = document.createElement('div');
		const div1 = document.createElement('div');
		const div2 = document.createElement('div');
		const a = document.createElement('a');
		const p = document.createElement('p');

		div.setAttribute('class','usersDiv');
		div1.setAttribute('class','userLeft');
		div2.setAttribute('class','userRight');

		 a.innerHTML = userUser.email;
			if(userUser.blocked=='NOTBLOCKED')
			{
				a.href='/users/block/'+userUser.email;
			}
			else
			{
				a.href='/users/unblock/'+userUser.email;
			}
			
		main.appendChild(div);
		div.appendChild(div1);
		div.appendChild(div2);
		div1.appendChild(a);
		div2.appendChild(p);
       
	});
		
		

	}) ();
});