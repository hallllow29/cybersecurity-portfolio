---
title: My Portfolio
---

# Hi — I'm Hallllow29

## Projects
{% for p in site.projects %}
- [{{ p.title }}]({{ p.url }}) — {{ p.excerpt | strip_html | truncatewords:20 }}
{% endfor %}

## CTFs
{% for c in site.ctfs %}
- [{{ c.title }}]({{ c.url }})
{% endfor %}
