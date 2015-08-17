The 3 stages are commited separately and pushed to Github.

Assume paragraphs are separated by \n
Assume sentences are separated by .|!|?
Assume tokens are seprated by a space.
Assume four patterns are defined from NER.txt, i.e. U of U; U de U; U ap U
and U numeric, U for Upper case.

To improve:
Define a library or use existing library to identify known entities;
Define a library to handle "'", e.g. won't=will not
Improve XML output: to make the XmlEncoder work, the sentenceID has to start from 1 in the xml document by adding 1 from the origla Token object. While it start from 0 in the original data.

