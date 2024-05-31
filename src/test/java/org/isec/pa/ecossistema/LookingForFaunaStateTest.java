package org.isec.pa.ecossistema;

import org.isec.pa.ecossistema.model.data.Fauna;
import org.isec.pa.ecossistema.model.fsm.FaunaContext;
import org.isec.pa.ecossistema.model.fsm.FaunaState;
import org.isec.pa.ecossistema.model.fsm.FaunaStates.LookingForFaunaState;
import org.isec.pa.ecossistema.utils.Area;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

public class LookingForFaunaStateTest {

    private FaunaContext context;
    private Fauna fauna;
    private LookingForFaunaState lookingForFaunaState;

    @BeforeEach
    public void setUp() {
        context = mock(FaunaContext.class);
        fauna = mock(Fauna.class);
        lookingForFaunaState = new LookingForFaunaState(context, fauna);
    }

    @Test
    public void testEvolve_FaunaNotAlive() {
        when(fauna.checkIfAlive()).thenReturn(false);

        lookingForFaunaState.evolve();

        verify(fauna, times(1)).checkIfAlive();
        verifyNoMoreInteractions(fauna);
        verifyNoInteractions(context);
    }

    @Test
    public void testEvolve_FaunaForcaLessThan50() {
        when(fauna.checkIfAlive()).thenReturn(true);
        when(fauna.getForca()).thenReturn(40.0);

        lookingForFaunaState.evolve();

        verify(fauna, times(1)).checkIfAlive();
        verify(fauna, times(1)).getForca();
        verify(context, times(1)).changeState(any(FaunaState.LOOKING_FOR_FLORA.getInstance(context, fauna).getClass()));
    }

    @Test
    public void testEvolve_FaunaHuntSuccess() {
        when(fauna.checkIfAlive()).thenReturn(true);
        when(fauna.getForca()).thenReturn(60.0);
        when(fauna.hunt()).thenReturn(true);

        lookingForFaunaState.evolve();

        verify(fauna, times(1)).checkIfAlive();
        verify(fauna, times(1)).getForca();
        verify(fauna, times(1)).hunt();
        verifyNoMoreInteractions(fauna);
        verifyNoInteractions(context);
    }

    @Test
    public void testEvolve_FaunaHuntFail_TargetFound() {
        when(fauna.checkIfAlive()).thenReturn(true);
        when(fauna.getForca()).thenReturn(60.0);
        when(fauna.hunt()).thenReturn(false);
        Area targetFauna = new Area(0, 0, 0, 0);
        when(fauna.lookForWeakestFauna()).thenReturn(targetFauna);

        lookingForFaunaState.evolve();

        verify(fauna, times(1)).checkIfAlive();
        verify(fauna, times(1)).getForca();
        verify(fauna, times(1)).hunt();
        verify(fauna, times(1)).lookForWeakestFauna();
        verify(fauna, times(1)).setTarget(targetFauna);
        verify(fauna, times(1)).getDirectionOfTarget();
        verifyNoMoreInteractions(fauna);
        verifyNoInteractions(context);
    }

    @Test
    public void testEvolve_FaunaHuntFail_NoTargetFound() {
        when(fauna.checkIfAlive()).thenReturn(true);
        when(fauna.getForca()).thenReturn(60.0);
        when(fauna.hunt()).thenReturn(false);
        when(fauna.lookForWeakestFauna()).thenReturn(null);

        lookingForFaunaState.evolve();

        verify(fauna, times(1)).checkIfAlive();
        verify(fauna, times(1)).getForca();
        verify(fauna, times(1)).hunt();
        verify(fauna, times(1)).lookForWeakestFauna();
        verify(fauna, times(1)).setDirection(null);
        verify(fauna, times(1)).setTarget(null);
        verify(fauna, times(1)).getDirectionOfTarget();
        verifyNoMoreInteractions(fauna);
        verifyNoInteractions(context);
    }
}

