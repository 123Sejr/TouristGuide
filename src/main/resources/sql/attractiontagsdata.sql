USE touristguide;

# delete from attraction_tags;
# kør delete først hvis noget nyt tilføjes til lsiten

INSERT INTO attraction_tags (attraction_id, tag_id)
VALUES (1, 3), # tivoli, amusement park
       (1, 6), # tivoli, kid
       (2,4), # kronborg, slot
       (2, 5), # kronborg, historie
       (2, 1), # kronborg, arkitekt
       (2,7), # kronborg, museum
       (3,1), # rundetårn, arkitekt
       (3, 5), # rundetårn, historie
       (4, 3), # legoland, amusement park
       (4, 6), # legoland, kid
       (5, 7), # hca hus, museum
       (5,5), # hca hus, hist
       (5,6), # hca hus, kid
       (6, 8), # grenen, natur
       (7, 5), # jellingesten, hist
       (8, 9), # randers regnskov, zoo
       (9, 5), # vikingeskib, hist
       (9, 7), # vikingeskib, museum
       (10, 7), # lejre, museum
       (11, 1), # gamle by, arkitekt
       (12, 2), # aros, kunst
       (12, 7), # aros, museum
       (13, 8), # stevns, natur
       (14, 8), # vadehavet, natur
       (15, 3), # bakken, amusement park
       (16, 7); # søfart, museum