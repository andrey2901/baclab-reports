function incude(name) {
	var link = document.querySelector('link[rel="import"][href="' + name
			+ '.html"]');
	var content = link.import.querySelector('#' + name);
	document.body.querySelector('#wrapper')
			.appendChild(content.cloneNode(true));
}

function importHTML(elem, name) {
	var root = document.querySelector('div[id="' + elem + '"]');
	var ajax = new XMLHttpRequest();
	ajax.open("GET", name + ".html", false);
	ajax.send();
	root.innerHTML += ajax.responseText;
}