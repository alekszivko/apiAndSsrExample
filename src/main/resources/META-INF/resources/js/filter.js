function filter() {
  let criteria = document.getElementById('searchfield').value.toUpperCase();
  let li = document.getElementsByTagName('li');

  for (let i = 0; i < li.length; i++) {
    if (li[i].innerText.toUpperCase().indexOf(criteria) < 0) {
      li[i].style.display = "none"
    } else {
      li[i].style.display = ''
    }
  }
}
