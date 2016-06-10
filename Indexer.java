import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.demo.IndexFiles;

import java.io.File;
import java.io.FileFilter;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by elnaz on 08.06.16.
 */
public class Indexer {

    private IndexWriter writer;

    public Indexer(){

    }

    //Create and Configure an IndexWriter
    public Indexer(String dir) throws IOException {
        Directory indexDir = FSDirectory.open(Paths.get(dir));
        Analyzer analyzer = null; //new StandardAnalyzer();
        IndexWriterConfig cfg = new IndexWriterConfig(analyzer);
        cfg.setOpenMode(IndexWriterConfig.OpenMode.CREATE);
        writer = new IndexWriter(indexDir, cfg);
    }

    //A document with Fields
    protected Document getDocument(File f) throws Exception {
        Document doc = new Document();
        doc.add(new TextField("contents", new FileReader(f)));
        doc.add(new StringField("filename", f.getName(), Field.Store.YES));
        doc.add(new StringField("fullpath", f.getCanonicalPath(), Field.Store.YES));
        return doc;
    }

    //Use the IndexWriter
    private void indexFile(File f) throws Exception {
        Document doc = getDocument(f);
        writer.addDocument(doc);
    }

    //Index a Directory
    public int index(String dataDir, FileFilter filter) throws Exception {
        File[] files = new File(dataDir).listFiles();
        for (File f: files) {
            if (true && (filter == null || filter.accept(f))) {//?
                indexFile(f);
            }
        }
        return writer.numDocs();
    }

    //Closing the IndexWriter
    public void close() throws IOException {
        writer.close();
    }
}
