CREATE TABLE NIH_FOA.foa_type (
       code TEXT NOT NULL
     , category TEXT
     , title TEXT
     , description TEXT
     , info_link TEXT
     , PRIMARY KEY (code)
);

CREATE TABLE NIH_FOA.nih_ic (
       ic TEXT NOT NULL
     , title TEXT
     , description TEXT
     , logo_link TEXT
     , info_link TEXT
     , category TEXT
     , PRIMARY KEY (ic)
);

CREATE TABLE NIH_FOA.content (
       id INT NOT NULL
     , html TEXT
     , PRIMARY KEY (id)
);

CREATE TABLE NIH_FOA.investigator (
       uid INT NOT NULL
     , mode TEXT
     , last_check TIMESTAMP
     , PRIMARY KEY (uid)
);

CREATE TABLE NIH_FOA.guide_doc (
       id INT NOT NULL
     , primary_ic TEXT
     , title TEXT
     , purpose TEXT
     , rel_note TEXT
     , fa_direct_costs TEXT
     , guide_link TEXT
     , rel_date TIMESTAMP
     , intent_date TEXT
     , app_receipt_date TEXT
     , lard TIMESTAMP
     , file_name TEXT
     , doc_type TEXT
     , doc_num TEXT
     , expiration_date TIMESTAMP
     , PRIMARY KEY (id)
);

CREATE TABLE NIH_FOA.activity_code (
       id INT NOT NULL
     , code TEXT NOT NULL
     , PRIMARY KEY (id, code)
     , CONSTRAINT FK_activity_code_2 FOREIGN KEY (code)
                  REFERENCES NIH_FOA.foa_type (code)
     , CONSTRAINT FK_activity_code_1 FOREIGN KEY (id)
                  REFERENCES NIH_FOA.guide_doc (id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE NIH_FOA.interest (
       uid INT NOT NULL
     , id INT NOT NULL
     , PRIMARY KEY (uid, id)
     , CONSTRAINT FK_interest_2 FOREIGN KEY (id)
                  REFERENCES NIH_FOA.guide_doc (id) ON DELETE CASCADE ON UPDATE CASCADE
     , CONSTRAINT FK_interest_1 FOREIGN KEY (uid)
                  REFERENCES NIH_FOA.investigator (uid) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE NIH_FOA.part_ic (
       id INT NOT NULL
     , ic TEXT NOT NULL
     , PRIMARY KEY (id, ic)
     , CONSTRAINT FK_part_ic_2 FOREIGN KEY (ic)
                  REFERENCES NIH_FOA.nih_ic (ic)
     , CONSTRAINT FK_part_ic_1 FOREIGN KEY (id)
                  REFERENCES NIH_FOA.guide_doc (id) ON DELETE CASCADE ON UPDATE CASCADE
);

