ALTER TABLE teams
ADD abb VARCHAR(100);

UPDATE teams SET abb = 'BUF' WHERE id = '768c92aa-75ff-4a43-bcc0-f2798c2e1724';

