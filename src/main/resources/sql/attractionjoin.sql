use touristguide;

# Dette skal nok ikke bruges herfra, nu lægger jeg det lige her, så vi kan finde det.

select name as Name, description as Description, city_name as City, tag_name as Tag
from tourist_attraction
         join attraction_tags
              on attraction_tags.attraction_id = tourist_attraction.attraction_id
         join tag
              on tag.tag_id = attraction_tags.tag_id
         join city
              on city.city_id = tourist_attraction.city_id;