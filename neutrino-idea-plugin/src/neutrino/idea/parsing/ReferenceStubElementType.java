package neutrino.idea.parsing;

import com.intellij.psi.stubs.*;
import neutrino.idea.LiteralLanguage;
import neutrino.idea.psi.Reference;
import neutrino.idea.psi.ReferenceStub;
import neutrino.idea.psi.impl.ReferenceImpl;
import neutrino.idea.psi.impl.ReferenceStubImpl;

import java.io.IOException;

public class ReferenceStubElementType extends IStubElementType<ReferenceStub, Reference> {
    public ReferenceStubElementType() {
        super("REFERENCE", LiteralLanguage.INSTANCE);
    }

    public Reference createPsi(final ReferenceStub stub) {
        System.out.println("createPsi");
        return new ReferenceImpl(stub);
    }

    public ReferenceStub createStub(final Reference psi, final StubElement parentStub) {
        System.out.println("createStub");
        return new ReferenceStubImpl(parentStub);
    }

    public String getExternalId() {
        return "literal.reference";
    }

    public void serialize(final ReferenceStub stub, final StubOutputStream dataStream) throws IOException {
    }

    public ReferenceStub deserialize(final StubInputStream dataStream, final StubElement parentStub) throws IOException {
        return new ReferenceStubImpl(parentStub);
    }

    public void indexStub(final ReferenceStub stub, final IndexSink sink) {
    }
}