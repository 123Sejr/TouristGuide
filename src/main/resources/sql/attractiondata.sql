USE touristguide;
# i tvivl om tags skal oprettes her eller i attraction_tags i stedet. Læner mod det sidstnævnte.
INSERT INTO tourist_attraction (attraction_id, name, description, city_id)
VALUES (1, 'Tivoli', 'Forlystelsespark i indre København', 1),
       (2, 'Kronborg', 'Slot i Helsingør, hvor Hamlet foregår.', 8),
       (3, 'Rundetårn', 'Astronomisk tårn fra Christian IVs tid', 1),
       (4, 'Legoland', 'LEGOs forlystelsespark, hvor legoskulpturer udstilles.', 10),
       (5, 'H. C. Andersens Hus', 'Museum om eventyrforfatteren Hans Christian Andersen', 3),
       (6, 'Grenen','En sandbanke mellem havene Skagerak og Kattegat, der er det nordligste punkt i Danmark',9 ),
       (7, 'Jellingestenene', 'Runesten fra 900-tallet opført af hhv. Gorm den Gamle og Harald Blåstand.', 12),
       (8, 'Randers Regnskov', 'Tropisk zoologisk have.', 6),
       (9, 'Vikingeskibsmuseet', 'Museum om vikingetidens skibsfart, med både vrag og rekonstruerede skibe.', 7),
       (10, 'Sagnlandet Lejre', 'Arkæologisk frilandsmuseum og forskningscenter', 11),
       (11, 'Den Gamle By', 'Frilandsmuseum om bykultur', 2),
       (12, 'ARoS','Aarhus kunstmuseum', 2),
       (13, 'Stevns Klint', 'Klint med lerlag, der fortæller om geologiske tidsaldre', 13),
       (14, 'Vadehavscentret', 'Naturcenter ved vadehavet.', 5),
       (15, 'Bakken', 'Forlystelsepark i Dyrehaven.', 14),
       (16, 'M/S Museet for Søfart', 'Museum om den danske handelsflåde fra middelalderen til i dag.', 8);
