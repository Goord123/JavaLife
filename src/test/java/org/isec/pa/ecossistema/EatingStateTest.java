package org.isec.pa.ecossistema;

import org.isec.pa.ecossistema.model.data.Fauna;
import org.isec.pa.ecossistema.model.fsm.FaunaContext;
import org.isec.pa.ecossistema.model.fsm.FaunaStates.EatingState;
import org.isec.pa.ecossistema.model.fsm.FaunaStates.LookingForFloraState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

public class EatingStateTest {

    private FaunaContext context;
    private Fauna fauna;
    private EatingState eatingState;

    @BeforeEach
    public void setUp() {
        context = mock(FaunaContext.class);
        fauna = mock(Fauna.class);
        eatingState = new EatingState(context, fauna);
    }

    @Test
    public void testEvolve_FaunaNotAlive() {
        when(fauna.checkIfAlive()).thenReturn(false);

        eatingState.evolve();

        verify(fauna, times(1)).checkIfAlive();
        verifyNoMoreInteractions(fauna);
        verifyNoMoreInteractions(context);
    }

    @Test
    public void testEvolve_FaunaNotOnFlora() {
        when(fauna.checkIfAlive()).thenReturn(true);
        when(fauna.checkIfOnFlora()).thenReturn(false);

        eatingState.evolve();

        verify(fauna, times(1)).checkIfAlive();
        verify(fauna, times(1)).checkIfOnFlora();
        verify(context, times(1)).changeState(any(LookingForFloraState.class));
    }

    @Test
    public void testEvolve_FaunaOnFloraAndForcaLessThan100() {
        when(fauna.checkIfAlive()).thenReturn(true);
        when(fauna.checkIfOnFlora()).thenReturn(true);
        when(fauna.getForca()).thenReturn(50.0);

        eatingState.evolve();

        verify(fauna, times(1)).checkIfAlive();
        verify(fauna, times(2)).checkIfOnFlora();
        verify(fauna, times(1)).getForca();
        verify(fauna, times(1)).eat();
        verifyNoMoreInteractions(context);
    }

    @Test
    public void testEvolve_FaunaOnFloraAndForcaEqualOrGreaterThan100() {
        when(fauna.checkIfAlive()).thenReturn(true);
        when(fauna.checkIfOnFlora()).thenReturn(true);
        when(fauna.getForca()).thenReturn(100.0);

        eatingState.evolve();

        verify(fauna, times(1)).checkIfAlive();
        verify(fauna, times(2)).checkIfOnFlora();
        verify(fauna, times(1)).getForca();
        verify(context, times(1)).changeState(any(LookingForFloraState.class));
    }
}
