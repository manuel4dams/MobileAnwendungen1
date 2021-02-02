package com.adams.learningnames.helper;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;

import java.lang.ref.WeakReference;
import java.lang.reflect.InvocationTargetException;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import timber.log.Timber;

/**
 * @author Michael Ruf
 * @since 2015-05-07
 */
// NOTE We need to be able to define the default behaviour for
// the back stack, too (for the flag indeed).
// To achieve this it would be handy to implement a "Defaults" object
public class FragmentSwitcher<K extends Fragment> {

    private final WeakReference<Activity> activity;
    private final FragmentManager fragmentManager;
    private final int contentContainerId;
    private int defaultEnterAnimationResource = 0;
    private int defaultExitAnimationResource = 0;
    private int defaultPopEnterAnimationResource = 0;
    private int defaultPopExitAnimationResource = 0;

    /**
     * Sets the support fragment manager used to replace fragments.
     *
     * @param fragmentManager    The support fragment manager for transactions
     * @param contentContainerId The id of the view used as content container to insert the fragments in
     */
    public FragmentSwitcher(Activity activity, FragmentManager fragmentManager,
                            int contentContainerId) {
        this.activity = new WeakReference<>(activity);
        this.fragmentManager = fragmentManager;
        this.contentContainerId = contentContainerId;
    }

    /**
     * Returns the current default enter animation resource.
     *
     * @return Default enter animation resource
     */
    public int getDefaultEnterAnimationResource() {
        return defaultEnterAnimationResource;
    }

    /**
     * Sets the default animation resource used as enter animation if no custom animation is given
     * in the performing calls.
     *
     * @param res Default fragment enter animation if nothing else provided in the performing call
     */
    public void setDefaultEnterAnimationResource(int res) {
        defaultEnterAnimationResource = res;
    }

    /**
     * Returns the current default exit animation resource.
     *
     * @return Default exit animation resource
     */
    public int getDefaultExitAnimationResource() {
        return defaultExitAnimationResource;
    }

    /**
     * Sets the default animation resource used as exit animation if no custom animation is given
     * in the performing calls.
     *
     * @param res Default fragment exit animation if nothing else provided in the performing call
     */
    public void setDefaultExitAnimationResource(int res) {
        defaultExitAnimationResource = res;
    }

    /**
     * Returns the current default pop enter animation resource.
     *
     * @return Default pop enter animation resource
     */
    public int getDefaultPopEnterAnimationResource() {
        return defaultPopEnterAnimationResource;
    }

    /**
     * Sets the default animation resource used as pop enter animation if no custom animation is given
     * in the performing calls.
     *
     * @param res Default fragment pop enter animation if nothing else provided in the performing call
     */
    public void setDefaultPopEnterAnimationResource(int res) {
        defaultPopEnterAnimationResource = res;
    }

    /**
     * Returns the current default pop exit animation resource.
     *
     * @return Default pop animation resource
     */
    public int getDefaultPopExitAnimationResource() {
        return defaultPopExitAnimationResource;
    }

    /**
     * Sets the default animation resource used as pop exit animation if no custom animation is given
     * in the performing calls.
     *
     * @param res Default fragment pop exit animation if nothing else provided in the performing call
     */
    public void setDefaultPopExitAnimationResource(int res) {
        defaultPopExitAnimationResource = res;
    }

    /**
     * Returns the currently displayed fragment.
     *
     * @return Currently displayed fragment.
     */
    @SuppressWarnings("unchecked")
    public <T extends K> T getCurrentFragment() throws ClassCastException {
        return (T) fragmentManager.findFragmentById(contentContainerId);
    }

    /**
     * Replaces the currently displayed fragment with the fragment class provided as first argument.
     * If the displayed fragment is of the class provided, nothing will happen.
     *
     * @param fragmentClass The fragments class of the fragment that shell get created and replaced
     */
    public void replace(@NonNull Class<? extends K> fragmentClass) {
        replace(fragmentClass, false, null);
    }

    /**
     * Replaces the currently displayed fragment with the fragment class provided as first argument.
     * If the displayed fragment is of the class provided, nothing will happen.
     *
     * @param fragmentClass    The fragments class of the fragment that shell get created and replaced
     * @param backStackEnabled Enables the back stack for the replacement
     */
    public void replace(@NonNull Class<? extends K> fragmentClass, boolean backStackEnabled) {
        replace(fragmentClass, backStackEnabled, null);
    }

    /**
     * Replaces the currently displayed fragment with the fragment class provided as first argument.
     * If the displayed fragment is of the class provided, nothing will happen.
     *
     * @param fragmentClass The fragments class of the fragment that shell get created and replaced
     * @param arguments     The arguments the fragment shell receive
     */
    public void replace(@NonNull Class<? extends K> fragmentClass, Bundle arguments) {
        replace(fragmentClass, false, arguments);
    }

    /**
     * Replaces the currently displayed fragment with the fragment class provided as first argument.
     * If the displayed fragment is of the class provided, nothing will happen.
     *
     * @param fragmentClass  The fragments class of the fragment that shell get created and replaced
     * @param enterAnimation The animation used to enter the fragment
     * @param exitAnimation  The animation used to exit the fragment
     */
    public void replace(@NonNull Class<? extends K> fragmentClass, int enterAnimation, int exitAnimation) {
        replace(fragmentClass, false, null, enterAnimation, exitAnimation);
    }

    /**
     * Replaces the currently displayed fragment with the fragment class provided as first argument.
     * If the displayed fragment is of the class provided, nothing will happen.
     *
     * @param fragmentClass     The fragments class of the fragment that shell get created and replaced
     * @param enterAnimation    The animation used to enter the fragment
     * @param exitAnimation     The animation used to exit the fragment
     * @param popExitAnimation  The animation used to push the fragment to the stack
     * @param popEnterAnimation The animation used to pop the fragment from the stack
     */
    public void replace(@NonNull Class<? extends K> fragmentClass, int enterAnimation, int exitAnimation, int popExitAnimation, int popEnterAnimation) {
        replace(fragmentClass, false, null, enterAnimation, exitAnimation, popExitAnimation, popEnterAnimation);
    }

    /**
     * Replaces the currently displayed fragment with the fragment class provided as first argument.
     * If the displayed fragment is of the class provided, nothing will happen.
     *
     * @param fragmentClass    The fragments class of the fragment that shell get created and replaced
     * @param backStackEnabled Enables the back stack for the replacement
     * @param arguments        The arguments the fragment shell receive
     */
    public void replace(@NonNull Class<? extends K> fragmentClass, boolean backStackEnabled, Bundle arguments) {
        replace(fragmentClass, backStackEnabled, arguments, defaultEnterAnimationResource, defaultExitAnimationResource);
    }

    /**
     * Replaces the currently displayed fragment with the fragment class provided as first argument.
     * If the displayed fragment is of the class provided, nothing will happen.
     *
     * @param fragmentClass  The fragments class of the fragment that shell get created and replaced
     * @param arguments      The arguments the fragment shell receive
     * @param enterAnimation The animation used to enter the fragment
     * @param exitAnimation  The animation used to exit the fragment
     */
    public void replace(@NonNull Class<? extends K> fragmentClass, Bundle arguments, int enterAnimation, int exitAnimation) {
        replace(fragmentClass, false, arguments, enterAnimation, exitAnimation);
    }

    /**
     * Replaces the currently displayed fragment with the fragment class provided as first argument.
     * If the displayed fragment is of the class provided, nothing will happen.
     *
     * @param fragmentClass    The fragments class of the fragment that shell get created and replaced
     * @param arguments        The arguments the fragment shell receive
     * @param backStackEnabled Enables the back stack for the replacement
     * @param enterAnimation   The animation used to enter the fragment
     * @param exitAnimation    The animation used to exit the fragment
     */
    public void replace(@NonNull Class<? extends K> fragmentClass, boolean backStackEnabled, Bundle arguments, int enterAnimation, int exitAnimation) {
        replace(fragmentClass, backStackEnabled, arguments, enterAnimation, exitAnimation, defaultPopExitAnimationResource, defaultPopEnterAnimationResource);
    }

    /**
     * Replaces the currently displayed fragment with the fragment class provided as first argument.
     * If the displayed fragment is of the class provided, nothing will happen.
     *
     * @param fragmentClass     The fragments class of the fragment that shell get created and replaced
     * @param arguments         The arguments the fragment shell receive
     * @param backStackEnabled  Enables the back stack for the replacement
     * @param enterAnimation    The animation used to enter the fragment
     * @param exitAnimation     The animation used to exit the fragment
     * @param popExitAnimation  The animation used to push the fragment to the stack
     * @param popEnterAnimation The animation used to pop the fragment from the stack
     */
    public void replace(@NonNull Class<? extends K> fragmentClass, boolean backStackEnabled, Bundle arguments, int enterAnimation, int exitAnimation, int popExitAnimation, int popEnterAnimation) {
        synchronized (fragmentManager) {
            try {
                // Cancel the replacement when the
                // * fragment manager is destroyed
                // * WeakReference to activity is recycled
                // * The activity is currently finishing
                // * The activity is already destroyed
                if (fragmentManager.isDestroyed() || activity.get() == null || activity.get().isFinishing() ||
                        (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1 &&
                                activity.get().isDestroyed())) {
                    return;
                }

                // Check whether the current displayed fragment has the same tag
                if (getCurrentFragment() == null || getCurrentFragment().getClass() != fragmentClass) {
                    Fragment newFragment = fragmentClass.getConstructor().newInstance();
                    newFragment.setArguments(arguments);

                    FragmentTransaction transaction = fragmentManager.beginTransaction();
                    if (backStackEnabled) {
                        transaction.addToBackStack(fragmentClass.getName());
                    }
                    transaction.setCustomAnimations(enterAnimation, exitAnimation, popEnterAnimation, popExitAnimation);
                    transaction.replace(contentContainerId, newFragment);
                    // Note we allow the state loss because the activity may not exist anymore
                    // @see https://fabric.io/100things/android/apps/de.onehundretthings.android/issues/56f4e88affcdc042501fc6b1
                    // @see http://stackoverflow.com/questions/14177781/java-lang-illegalstateexception-can-not-perform-this-action-after-onsaveinstanc
                    transaction.commitAllowingStateLoss();
                    //transaction.commit();
                    Timber.i("Fragment created: %s", fragmentClass.getName());
                } else {
                    Timber.i("Fragment already current: %s", fragmentClass.getName());
                }
            } catch (InstantiationException e) {
                Timber.e("%s is not instantiable.", fragmentClass.getName());
            } catch (NoSuchMethodException e) {
                Timber.e("%s constructor not found.", fragmentClass.getName());
            } catch (IllegalAccessException e) {
                Timber.e("%s constructor/class not accessible.", fragmentClass.getName());
            } catch (InvocationTargetException e) {
                Timber.e(e.getTargetException(),
                        "%s invocation threw an exception.", fragmentClass.getName());
            }
        }
    }

    /**
     * Pops the fragment managers back stack and returns the last displayed fragment if it was added.
     * The animation that is used here, is the one that was specified while replacing to the current
     * fragment.
     */
    public void popBackStack() {
        fragmentManager.popBackStack();
    }

    /**
     * Clears the back stack and display the first added one.
     */
    public void clearBackStack() {
        fragmentManager.popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }

    /**
     * Return the number of entries currently in the back stack.
     *
     * @return Count of the stacked entries
     */
    public int getBackStackEntryCount() {
        return fragmentManager.getBackStackEntryCount();
    }
}
