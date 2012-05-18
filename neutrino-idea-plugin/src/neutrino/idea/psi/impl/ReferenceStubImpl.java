package neutrino.idea.psi.impl;

import com.intellij.psi.stubs.StubBase;
import com.intellij.psi.stubs.StubElement;
import neutrino.idea.parsing.LiteralElementTypes;
import neutrino.idea.psi.Reference;
import neutrino.idea.psi.ReferenceStub;

public class ReferenceStubImpl extends StubBase<Reference> implements ReferenceStub {
    public ReferenceStubImpl(final StubElement parent) {
        super(parent, LiteralElementTypes.REFERENCE);
        System.out.println("Created ReferenceStubImpl");
    }
}