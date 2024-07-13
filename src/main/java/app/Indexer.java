package app;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.ByteBuffersDirectory;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;

public class Indexer 
{
	private static ByteBuffersDirectory directory;
	private static Analyzer analyzer;
	private static IndexWriterConfig config;
	private static IndexWriter indexWriter;
	
	private static int fileType = 1;

	public Indexer() throws IOException {
		super();
				
		directory = new ByteBuffersDirectory();
		analyzer = new StandardAnalyzer();
		config = new IndexWriterConfig(analyzer);
		indexWriter = new IndexWriter(directory, config);
	}
    
    public void addIndex(String adress) throws Exception {
    	
    	FileFilter filter = new TextFilesFilter();
        IndexWriterConfig config2 = new IndexWriterConfig(analyzer);
        indexWriter = new IndexWriter(directory, config2 );
        File fil = new File(adress);
		try {
			if( filter.accept(fil))
				this.indexFile(fil);
		} finally {
			this.close();
		}  
    }
    
    public void changeFileType(String fileType) {
    	if( fileType == ".txt") 
    		this.fileType = 1;
    	
    	if( fileType == ".pdf") 
    		this.fileType = 2;
    	
    }
    
    public void indexer(String indexAddress) throws Exception {	
		try {
			this.index(indexAddress, new TextFilesFilter());
		} finally {
			this.close();
		}   		
    }
    
    public String busqueda(String searchText) throws IOException, ParseException {
    	System.out.println("\n # Ahora empieza la busqueda ---> #\n");       
    	
        // Create an IndexSearcher
        DirectoryReader directoryReader = DirectoryReader.open(directory);
        IndexSearcher indexSearcher = new IndexSearcher(directoryReader);

        // Parse a query
        QueryParser queryParser = new QueryParser("content", analyzer);
        Query query = queryParser.parse(searchText);

        // Search the index
        TopDocs topDocs = indexSearcher.search(query, 10);
        
        String result = "";

        // Display the results
        System.out.println("\nFound " + topDocs.totalHits.value + " hits.");
        for (int i = 0; i < topDocs.scoreDocs.length; i++) {
            int docId = topDocs.scoreDocs[i].doc;
            Document doc = indexSearcher.doc(docId);
            String name = doc.get("name");
            String content = doc.get("content");
            System.out.println("\nFile Name : " + name);
            result += name + "\n";
        }

        directoryReader.close();
        return result;
    }
    
    
	public int index(String dataDir, FileFilter filter) throws Exception {
		File[] files = new File(dataDir).listFiles();
		for (File f: files) {
			if (!f.isDirectory() && !f.isHidden() && f.exists() && f.canRead() 
					&& (filter == null || filter.accept(f))) {
				indexFile(f);
			}
		}
		
		System.out.println(" \nNumber of indexed elements : " + indexWriter.numRamDocs());
		return indexWriter.numRamDocs();
	}
	
	private void indexFile(File f) throws Exception {
		System.out.println("Indexing " + f.getCanonicalPath());
		Document doc = getDocument(f);
		indexWriter.addDocument(doc);
	}
	
	
	protected Document getDocument(File f) throws Exception {
        Document doc = new Document();
        System.out.println("File Name : "+f.getName());
        doc.add(new StringField("name", f.getName(), Field.Store.YES));
        
        if ( fileType == 1) {
        	doc.add(new TextField("content", ReadFile.read(f.getPath()), Field.Store.YES));
        }
        
        if ( fileType == 2) {        	
        	doc.add(new TextField("content", ReadPdf.readPdf(f.getPath()), Field.Store.YES));
        }
                 
        return doc;
	}	
	
	
	public void close() throws IOException {
		indexWriter.close();
	}
	
	
	private static class TextFilesFilter implements FileFilter {
		public boolean accept(File path) {
	        if ( fileType == 2) {        	
	        	return path.getName().toLowerCase().endsWith(".pdf");
	        }
	        
			return path.getName().toLowerCase().endsWith(".txt");
		}
	}
}