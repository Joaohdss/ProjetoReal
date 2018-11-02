package android.support.v4.app;

import android.util.Log;
import android.view.View;
import android.view.Window;
import com.actionbarsherlock.ActionBarSherlock.OnCreatePanelMenuListener;
import com.actionbarsherlock.ActionBarSherlock.OnMenuItemSelectedListener;
import com.actionbarsherlock.ActionBarSherlock.OnPreparePanelListener;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;

import java.util.ArrayList;

/** I'm in ur package. Stealing ur variables. */
public abstract class _ActionBarSherlockTrojanHorse extends FragmentActivity implements OnCreatePanelMenuListener, OnPreparePanelListener, OnMenuItemSelectedListener {
    private static final boolean DEBUG = false;
    private static final String TAG = "_ActionBarSherlockTrojanHorse";

    /** Fragment interface for menu creation callback. */
    public interface OnCreateOptionsMenuListener {
        public void onCreateOptionsMenu(Menu menu, MenuInflater inflater);
    }
    /** Fragment interface for menu preparation callback. */
    public interface OnPrepareOptionsMenuListener {
        public void onPrepareOptionsMenu(Menu menu);
    }
    /** Fragment interface for menu item selection callback. */
    public interface OnOptionsItemSelectedListener {
      public boolean onOptionsItemSelected(MenuItem item);
    }

    private ArrayList<Fragment> mCreatedMenus;

    ///////////////////////////////////////////////////////////////////////////
    // Sherlock menu handling
    ///////////////////////////////////////////////////////////////////////////
    @Override
    public boolean onCreatePanelMenu(int featureId, Menu menu) {
        if (DEBUG) Log.d(TAG, "[onCreatePanelMenu] featureId: " + featureId + ", menu: " + menu);
        return onCreatePanelMenuAux(featureId, menu);}
	private boolean onCreatePanelMenuAux(int featureId, Menu menu) {
		if (featureId == Window.FEATURE_OPTIONS_PANEL) {
            return showCreatePainel(menu);}
        return false;}
	private boolean showCreatePainel(Menu menu) {
		boolean result = onCreateOptionsMenu(menu);
		if (DEBUG) Log.d(TAG, "[onCreatePanelMenu] activity create result: " + result);
		MenuInflater inflater = getSupportMenuInflater();
		boolean show = false;
		ArrayList<Fragment> newMenus = null;
		show = onCreateOptionsNewMenus(menu, inflater, show, newMenus);
		if (DEBUG) Log.d(TAG, "[onCreatePanelMenu] fragments create result: " + show);
		result |= show;
		if (DEBUG) Log.d(TAG, "[onCreatePanelMenu] returning " + result);
		return result;
	}
	private boolean onCreateOptionsNewMenus(Menu menu, MenuInflater inflater, boolean show,ArrayList<Fragment> newMenus) {
		if (mFragments.mActive != null) {
		    for (int i = 0; i < mFragments.mAdded.size(); i++) {
		        Fragment f = mFragments.mAdded.get(i);
		        if (f != null && !f.mHidden && f.mHasMenu && f.mMenuVisible && f instanceof OnCreateOptionsMenuListener) {
		            show = true;
		            ((OnCreateOptionsMenuListener)f).onCreateOptionsMenu(menu, inflater);
		            if (newMenus == null) {
		                newMenus = new ArrayList<Fragment>();
		            }
		            newMenus.add(f);
		        }
		    }
		}
		menuNull(newMenus);
		mCreatedMenus = newMenus;
		return show;
	}
	private void menuNull(ArrayList<Fragment> newMenus) {
		if (mCreatedMenus != null) {
		    for (int i = 0; i < mCreatedMenus.size(); i++) {
		        Fragment f = mCreatedMenus.get(i);
		        if (newMenus == null || !newMenus.contains(f)) {
		            f.onDestroyOptionsMenu();
		        }
		    }
		}
	}

    @Override
    public boolean onPreparePanel(int featureId, View view, Menu menu) {
        if (DEBUG) Log.d(TAG, "[onPreparePanel] featureId: " + featureId + ", view: " + view + " menu: " + menu);
        return onPreparePanelAux(featureId, menu);
    }
	private boolean onPreparePanelAux(int featureId, Menu menu) {
		if (featureId == Window.FEATURE_OPTIONS_PANEL) {
            boolean result = onPrepareOptionsMenu(menu);
            if (DEBUG) Log.d(TAG, "[onPreparePanel] activity prepare result: " + result);
            boolean show = false;
            show = mActiveNull(menu, show);
            if (DEBUG) Log.d(TAG, "[onPreparePanel] fragments prepare result: " + show);
            result |= show;
            result &= menu.hasVisibleItems();
            if (DEBUG) Log.d(TAG, "[onPreparePanel] returning " + result);
            return result;
        }
        return false;
	}

	private boolean mActiveNull(Menu menu, boolean show) {
		if (mFragments.mActive != null) {
		    for (int i = 0; i < mFragments.mAdded.size(); i++) {
		        Fragment f = mFragments.mAdded.get(i);
		        if (f != null && !f.mHidden && f.mHasMenu && f.mMenuVisible && f instanceof OnPrepareOptionsMenuListener) {
		            show = true;
		            ((OnPrepareOptionsMenuListener)f).onPrepareOptionsMenu(menu);
		        }
		    }
		}
		return show;
	}

    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        if (DEBUG) Log.d(TAG, "[onMenuItemSelected] featureId: " + featureId + ", item: " + item);

        if (featureId == Window.FEATURE_OPTIONS_PANEL) {
            if (onOptionsItemSelected(item)) {
                return true;
            }

            if (mFragments.mActive != null) {
                for (int i = 0; i < mFragments.mAdded.size(); i++) {
                    Fragment f = mFragments.mAdded.get(i);
                    if (f != null && !f.mHidden && f.mHasMenu && f.mMenuVisible && f instanceof OnOptionsItemSelectedListener) {
                        if (((OnOptionsItemSelectedListener)f).onOptionsItemSelected(item)) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    public abstract boolean onCreateOptionsMenu(Menu menu);

    public abstract boolean onPrepareOptionsMenu(Menu menu);

    public abstract boolean onOptionsItemSelected(MenuItem item);

    public abstract MenuInflater getSupportMenuInflater();
}
